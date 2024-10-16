package io.hhplus.concertreservationservice.domain.queue

interface QueueRepository {
    fun save(queueEntry: QueueEntry): QueueEntry
    fun findByToken(token: String): QueueEntry?
    fun count(): Long
    fun deleteByToken(token: String)
}
