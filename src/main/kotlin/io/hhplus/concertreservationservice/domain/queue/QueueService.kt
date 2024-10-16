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
        if (existingEntry != null) {
            return existingEntry
        }

        // 대기열 순번 계산
        val queuePosition = (queueRepository.countByStatus("waiting") + 1).toInt()

        // QueueEntry 생성
        val queueEntry = QueueEntry(
            userId = userId,
            queuePosition = queuePosition,
            status = "waiting"
        )

        return queueRepository.save(queueEntry)
    }

    fun getQueueStatus(userId: Long): QueueEntry? {
        return queueRepository.findByUserIdAndStatus(userId, "waiting")
    }

    fun processNextInQueue() {
        // 줄에서 맨 앞에 서있는 사용자 선택
        val nextEntry = queueRepository.findTopByOrderByQueuePositionAsc("waiting") ?: return

        // 상태 변경
        nextEntry.status = "completed"
        queueRepository.save(nextEntry)
    }

    fun calculateEstimatedWaitTime(queuePosition: Int): Long {
        // 대기 순번에 따른 예상 대기 시간 계산
        return queuePosition * WAIT_TIME_PER_PERSON
    }

}