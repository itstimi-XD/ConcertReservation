package io.hhplus.concertreservationservice.domain.queue

import io.hhplus.concertreservationservice.infrastructure.redis.RedisClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class QueueTokenServiceImpl(
    private val redisClient: RedisClient,
    @Value("\${queue.token.expirationMinutes}") private val tokenExpirationMinutes: Long
) : QueueTokenService {

    private val QUEUE_TOKEN_PREFIX = "queueToken:"

    override fun saveQueueToken(token: String, userId: Long) {
        val key = "$QUEUE_TOKEN_PREFIX$token"
        redisClient.set(key, userId.toString(), tokenExpirationMinutes)
    }

    override fun getUserIdByQueueToken(token: String): Long? {
        val key = "$QUEUE_TOKEN_PREFIX$token"
        val userIdStr = redisClient.get(key)
        return userIdStr?.toLong()
    }

    override fun deleteQueueToken(token: String) {
        val key = "$QUEUE_TOKEN_PREFIX$token"
        redisClient.delete(key)
    }
}
