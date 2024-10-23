package io.hhplus.concertreservationservice.domain.queue

import java.time.LocalDateTime

interface QueueRepository {
    fun findByQueueToken(queueToken: String): QueueEntry?
    fun save(queueEntry: QueueEntry): QueueEntry
    fun findById(id: Long): QueueEntry?
    fun findByUserIdAndStatus(userId: Long, status: QueueStatus): QueueEntry?
    fun deleteById(id: Long)
    fun count(): Long
    fun findTopByStatusOrderByQueuePositionAsc(status: QueueStatus): QueueEntry?
    fun countByStatus(status: QueueStatus): Long
    fun findAllByStatusAndUpdatedAtBefore(status: QueueStatus, updatedAt: LocalDateTime): List<QueueEntry>
    fun deleteAll(entries: List<QueueEntry>)

    // 콘서트 스케줄별 메서드 추가
    fun findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
        concertScheduleId: Long,
        status: QueueStatus,
        limit: Int
    ): List<QueueEntry>

    fun countByConcertScheduleIdAndStatus(
        concertScheduleId: Long,
        status: QueueStatus
    ): Long

    fun findByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: QueueStatus
    ): QueueEntry?

    fun deleteByUserId(userId: Long)
}