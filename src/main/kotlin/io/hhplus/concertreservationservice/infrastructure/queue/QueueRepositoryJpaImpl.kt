package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface QueueRepositoryJpaImpl : QueueRepository, JpaRepository<QueueEntry, Long> {

    override fun save(queueEntry: QueueEntry): QueueEntry

    override fun findById(id: Long): Optional<QueueEntry>

    override fun findByUserIdAndStatus(userId: Long, status: String): QueueEntry?

    override fun deleteById(id: Long)

    override fun count(): Long

    override fun countByStatus(status: String): Long

    override fun findTopByStatusOrderByQueuePositionAsc(status: String): QueueEntry?

    override fun findAllByStatusAndUpdatedAtBefore(status: String, updatedAt: LocalDateTime): List<QueueEntry>
}