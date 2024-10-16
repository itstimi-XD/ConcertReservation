package io.hhplus.concertreservationservice.domain.queue

import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class QueueService(
    private val queueRepository: QueueRepository
) {

    fun issueToken(userId: Long): QueueEntry {
        val token = UUID.randomUUID().toString()
        val queuePosition = queueRepository.count() + 1
        val estimatedWaitTime = queuePosition * 60L // 1인당 1분씩 대기

        val queueEntry = QueueEntry(
            token = token,
            userId = userId,
            queuePosition = queuePosition,
            issuedAt = LocalDateTime.now(),
            estimatedWaitTime = estimatedWaitTime
        )

        return queueRepository.save(queueEntry)
    }

    fun getQueueStatus(token: String): QueueEntry? {
        return queueRepository.findByToken(token)
    }

    fun deleteQueueEntry(token: String) {
        queueRepository.deleteByToken(token)
    }

}