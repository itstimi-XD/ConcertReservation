package io.hhplus.concertreservationservice.domain.queue

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class QueueService(
    private val queueRepository: QueueRepository
) {

    val WAIT_TIME_PER_PERSON = 60L

    fun registerUserInQueue(userId: Long): QueueEntry {
        // 이미 대기열에 존재하는지 확인
        val existingEntry = queueRepository.findByUserIdAndStatus(userId, "waiting")
        existingEntry?.let {
            return it
        }

        // 대기열 순번 계산
        val queuePosition = (queueRepository.countByStatus("waiting") + 1).toInt()

        // QueueEntry 생성
        val queueEntry = QueueEntry(
            userId = userId,
            queueToken = UUID.randomUUID().toString(),
            queuePosition = queuePosition,
            status = "waiting"
        )

        return queueRepository.save(queueEntry)
    }
//
//    fun getQueueStatus(userId: Long): QueueEntry? {
//        return queueRepository.findByUserIdAndStatus(userId, "waiting")
//    }

    fun getQueueStatus(queueToken: String): QueueEntry? {
        return queueRepository.findByQueueToken(queueToken)
    }

    fun processNextInQueue() {
        // 줄에서 맨 앞에 서있는 사용자 선택
        val nextEntry = queueRepository.findTopByStatusOrderByQueuePositionAsc("waiting") ?: return

        // 상태 변경
        nextEntry.status = "completed"
        queueRepository.save(nextEntry)
    }

    fun calculateEstimatedWaitTime(queuePosition: Int): Long {
        // 대기 순번에 따른 예상 대기 시간 계산
        return queuePosition * WAIT_TIME_PER_PERSON
    }

//    fun removeExpiredEntries(expirationTime: LocalDateTime) {
//        val expiredEntries = queueRepository.findAllByStatusAndUpdatedAtBefore("completed", expirationTime)
//        expiredEntries.forEach { entry ->
//            queueRepository.delete(entry)
//        }
//    }

}