package io.hhplus.concertreservationservice.infrastructure.redis

interface RedisClient {
    fun set(key: String, value: String, expirationMinutes: Long)
    fun get(key: String): String?
    fun delete(key: String)
}