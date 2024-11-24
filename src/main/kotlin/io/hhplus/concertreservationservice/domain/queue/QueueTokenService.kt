package io.hhplus.concertreservationservice.domain.queue

interface QueueTokenService {
    fun saveQueueToken(token: String, userId: Long)
    fun getUserIdByQueueToken(token: String): Long?
    fun deleteQueueToken(token: String)
}
