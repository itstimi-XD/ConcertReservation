package io.hhplus.concertreservationservice.domain.queue

import java.time.LocalDateTime

interface QueueRepository {
    fun findByQueueToken(queueToken: String): QueueEntry?
    fun save(queueEntry: QueueEntry): QueueEntry
    fun findById(id: Long): QueueEntry?
    fun findByUserIdAndStatus(userId: Long, status: String): QueueEntry?
    fun deleteById(id: Long)
    fun count(): Long
    fun findTopByStatusOrderByQueuePositionAsc(status: String): QueueEntry?
    fun countByStatus(status: String): Long
    fun findAllByStatusAndUpdatedAtBefore(status: String, updatedAt: LocalDateTime): List<QueueEntry>
    fun deleteAll(entries: List<QueueEntry>)

    // 콘서트 스케줄별 메서드 추가
    fun findByConcertScheduleIdAndStatusOrderByQueuePositionAsc(
        concertScheduleId: Long,
        status: String,
        limit: Int
    ): List<QueueEntry>

    fun countByConcertScheduleIdAndStatus(
        concertScheduleId: Long,
        status: String
    ): Long

    fun findByUserIdAndConcertScheduleIdAndStatus(
        userId: Long,
        concertScheduleId: Long,
        status: String
    ): QueueEntry?

    fun deleteByUserId(userId: Long)
}
