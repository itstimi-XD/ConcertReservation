package io.hhplus.concertreservationservice.domain.user

interface UserRepository {
    fun findByName(username: String): User?
}