package io.hhplus.concertreservationservice.domain.user

interface UserRepository {
    fun findByName(username: String): User?
    fun findByIdForUpdate(id: Long): User?
    fun save(user: User): User
    fun findById(id: Long): User?
}