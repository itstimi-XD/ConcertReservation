package io.hhplus.concertreservationservice.domain.queue

import io.hhplus.concertreservationservice.exception.ErrorType
import io.hhplus.concertreservationservice.exception.QueueException
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*


@Service
class QueueService(
    private val queueRepository: QueueRepository,
    // TODO : WaitingQueue 도메인 객체가 아래 값들을 가지고 있도록 수정 필요
    @Value("\${scheduler.queueProcess.passLimit}") private val passLimit: Int,
    @Value("\${scheduler.queueCleanup.expirationMinutes}") private val expirationMinutes: Long
) {
    companion object {
        const val WAIT_TIME_PER_PERSON = 60L
    }

    fun isValidQueueToken(token: String): Boolean {
        val queueEntry = queueRepository.findByQueueToken(token)
            ?: return false
        return queueEntry.status == QueueStatus.COMPLETED
    }

    fun isUserAlreadyInQueue(userId: Long, concertScheduleId: Long): Boolean {
        return queueRepository.existsByUserIdAndConcertScheduleIdAndStatus(userId, concertScheduleId, QueueStatus.WAITING)
    }

    @Transactional
    fun registerUserInQueue(userId: Long, concertScheduleId: Long): QueueEntry {
        // 이미 대기열에 존재하는지 확인
        if (isUserAlreadyInQueue(userId, concertScheduleId)) {
            throw QueueException(ErrorType.USER_ALREADY_IN_QUEUE, "userId: $userId, concertScheduleId: $concertScheduleId")
        }

        // 대기열 순번 계산
        val queuePosition = (queueRepository.countByStatus(QueueStatus.WAITING) + 1).toInt()

        // QueueEntry 생성
        val queueEntry = QueueEntry(
            userId = userId,
            queueToken = UUID.randomUUID().toString(),
            concertScheduleId = concertScheduleId,
            queuePosition = queuePosition,
            status = QueueStatus.WAITING
        )

        return queueRepository.save(queueEntry)
    }

    fun getQueueStatus(queueToken: String): QueueEntry? {
        return queueRepository.findByQueueToken(queueToken)
            ?: throw QueueException(ErrorType.INVALID_QUEUE_TOKEN, "queueToken: $queueToken")
    }

    fun processNextInQueue() {
        // 줄에서 맨 앞에 서있는 사용자 선택
        val nextEntry = queueRepository.findTopByStatusOrderByQueuePositionAsc(QueueStatus.WAITING)
            ?: throw QueueException(ErrorType.QUEUE_EMPTY)

        // 상태 변경
        nextEntry.status = QueueStatus.COMPLETED
        nextEntry.updatedAt = LocalDateTime.now()
        queueRepository.save(nextEntry)
    }

    fun calculateEstimatedWaitTime(queuePosition: Int): Long {
        // 대기 순번에 따른 예상 대기 시간 계산 (초 단위)
        return (queuePosition - 1) * WAIT_TIME_PER_PERSON
    }

    fun processNextUsersByConcertSchedule(concertScheduleId: Long) {
        val waitingUsers = queueRepository.findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
            concertScheduleId,
            QueueStatus.WAITING,
            passLimit
        )
        waitingUsers.forEach { queueEntry ->
            queueEntry.status = QueueStatus.COMPLETED
            queueEntry.updatedAt = LocalDateTime.now()
            queueRepository.save(queueEntry)
        }
    }

    fun removeExpiredEntries() {
        val expirationTime = LocalDateTime.now().minusMinutes(expirationMinutes)
        val expiredEntries = queueRepository.findAllByStatusAndUpdatedAtBefore(QueueStatus.COMPLETED, expirationTime)
        queueRepository.deleteAll(expiredEntries)
    }

    fun deleteQueueByUserId(userId: Long) {
        queueRepository.deleteByUserId(userId)
    }
}