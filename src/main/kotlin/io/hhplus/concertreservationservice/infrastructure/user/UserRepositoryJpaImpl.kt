package io.hhplus.concertreservationservice.infrastructure.user

import io.hhplus.concertreservationservice.domain.user.User
import io.hhplus.concertreservationservice.domain.user.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepositoryJpaImpl : UserRepository, JpaRepository<User, Long> {

//    override fun save(user: User): User
//
//    override fun findById(id: Long): Optional<User>

    override fun findByName(username: String): User?

//    override fun deleteById(id: Long)
//
//    override fun count(): Long
}