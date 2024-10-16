package io.hhplus.concertreservationservice.domain.queue

import java.time.LocalDateTime
import java.util.*

interface QueueRepository {
    fun save(queueEntry: QueueEntry): QueueEntry
    fun findById(id: Long): Optional<QueueEntry>
    fun findByUserIdAndStatus(userId: Long, status: String): QueueEntry?
    fun deleteById(id: Long)
    fun count(): Long
    fun findTopByStatusOrderByQueuePositionAsc(status: String): QueueEntry?
    fun countByStatus(status: String): Long
    fun findAllByStatusAndUpdatedAtBefore(status: String, updatedAt: LocalDateTime): List<QueueEntry>
}

