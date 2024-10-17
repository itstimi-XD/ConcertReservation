package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface JpaQueueRepository : JpaRepository<QueueEntry, Long> {
    fun findByQueueToken(queueToken: String): QueueEntry?
    fun findByUserIdAndStatus(userId: Long, status: String): QueueEntry?
    fun findTopByStatusOrderByQueuePositionAsc(status: String): QueueEntry?
    fun countByStatus(status: String): Long
    fun findAllByStatusAndUpdatedAtBefore(status: String, updatedAt: LocalDateTime): List<QueueEntry>
    // 콘서트 스케줄별 메서드 추가
    @Query("SELECT q FROM QueueEntry q WHERE q.concertScheduleId = :concertScheduleId AND q.status = :status ORDER BY q.queuePosition ASC")
    fun findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
        @Param("concertScheduleId") concertScheduleId: Long,
        @Param("status") status: String,
        pageable: Pageable
    ): List<QueueEntry>

    fun countByConcertScheduleIdAndStatus(
        concertScheduleId: Long,
        status: String
    ): Long

    fun deleteByUserId(userId: Long)

    fun findByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: String
    ): QueueEntry?
}
