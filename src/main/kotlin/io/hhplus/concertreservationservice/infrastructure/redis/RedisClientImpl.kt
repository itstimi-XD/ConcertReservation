package io.hhplus.concertreservationservice.infrastructure.redis

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisClientImpl(
    private val redisTemplate: StringRedisTemplate
) : RedisClient {

    override fun set(key: String, value: String, expirationMinutes: Long) {
        redisTemplate.opsForValue().set(key, value, expirationMinutes, TimeUnit.MINUTES)
    }

    override fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key)
    }

    override fun delete(key: String) {
        redisTemplate.delete(key)
    }
}
