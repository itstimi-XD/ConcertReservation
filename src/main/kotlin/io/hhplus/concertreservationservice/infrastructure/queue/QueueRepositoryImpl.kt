package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import io.hhplus.concertreservationservice.domain.queue.QueueStatus
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class QueueRepositoryImpl(
    private val jpaQueueRepository: JpaQueueRepository
) : QueueRepository {

    override fun deleteAll(entries: List<QueueEntry>) {
        return jpaQueueRepository.deleteAll(entries)
    }

    override fun findAllByStatus(status: QueueStatus): List<QueueEntry> {
        return jpaQueueRepository.findAllByStatus(status)
    }

    override fun findByQueueToken(queueToken: String): QueueEntry? {
        return jpaQueueRepository.findByQueueToken(queueToken)
    }

    override fun save(queueEntry: QueueEntry): QueueEntry {
        return jpaQueueRepository.save(queueEntry)
    }

    override fun findById(id: Long): QueueEntry? {
        return jpaQueueRepository.findById(id).orElse(null)
    }

    override fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntry? {
        return jpaQueueRepository.findByUserIdAndStatus(userId, status)
    }

    override fun deleteById(id: Long) {
        jpaQueueRepository.deleteById(id)
    }

    override fun count(): Long {
        return jpaQueueRepository.count()
    }

    override fun findTopByStatusOrderByQueuePositionAsc(status: QueueStatus): QueueEntry? {
        return jpaQueueRepository.findTopByStatusOrderByQueuePositionAsc(status)
    }

    override fun countByStatus(status: QueueStatus): Long {
        return jpaQueueRepository.countByStatus(status)
    }

    override fun findAllByStatusAndUpdatedAtBefore(status: QueueStatus, updatedAt: LocalDateTime): List<QueueEntry> {
        return jpaQueueRepository.findAllByStatusAndUpdatedAtBefore(status, updatedAt)
    }

    override fun findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
        concertScheduleId: Long,
        status: QueueStatus,
        limit: Int
    ): List<QueueEntry> {
        val pageable = PageRequest.of(0, limit)
        return jpaQueueRepository.findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(concertScheduleId, status, pageable)
    }

    override fun countByConcertScheduleIdAndStatus(concertScheduleId: Long, status: QueueStatus): Long {
        return jpaQueueRepository.countByConcertScheduleIdAndStatus(concertScheduleId, status)
    }

    override fun findByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: QueueStatus
    ): QueueEntry? {
        return jpaQueueRepository.findByUserIdAndConcertScheduleIdAndStatus(userId, concertScheduleId, status)
    }

    override fun deleteByUserId(userId: Long) {
        jpaQueueRepository.deleteByUserId(userId)
    }

    override fun existsByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: QueueStatus
    ): Boolean {
        return jpaQueueRepository.existsByUserIdAndConcertScheduleIdAndStatus(userId, concertScheduleId, status)
    }
}