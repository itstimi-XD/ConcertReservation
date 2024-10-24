package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface JpaQueueRepository : JpaRepository<QueueEntry, Long> {
    fun findByQueueToken(queueToken: String): QueueEntry?
    fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntry?
    fun findTopByStatusOrderByQueuePositionAsc(status: QueueStatus): QueueEntry?
    fun countByStatus(status: QueueStatus): Long
    fun findAllByStatusAndUpdatedAtBefore(status: QueueStatus, updatedAt: LocalDateTime): List<QueueEntry>

    @Query("SELECT q FROM QueueEntry q WHERE q.concertScheduleId = :concertScheduleId AND q.status = :status ORDER BY q.queuePosition ASC")
    fun findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
        @Param("concertScheduleId") concertScheduleId: Long,
        @Param("status") status: QueueStatus,
        pageable: Pageable
    ): List<QueueEntry>

    fun countByConcertScheduleIdAndStatus(
        concertScheduleId: Long,
        status: QueueStatus
    ): Long

    fun deleteByUserId(userId: Long)

    fun findByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: QueueStatus
    ): QueueEntry?

    fun existsByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: QueueStatus
    ): Boolean
}