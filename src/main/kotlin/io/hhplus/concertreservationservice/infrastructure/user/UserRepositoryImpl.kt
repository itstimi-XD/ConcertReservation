package io.hhplus.concertreservationservice.infrastructure.user

import io.hhplus.concertreservationservice.domain.user.User
import io.hhplus.concertreservationservice.domain.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {

    override fun findByName(username: String): User? {
        return jpaUserRepository.findByName(username)
    }

    override fun findByIdForUpdate(id: Long): User? {
        return jpaUserRepository.findByIdForUpdate(id)
    }

    override fun save(user: User): User {
        return jpaUserRepository.save(user)
    }

    override fun findById(id: Long): User? {
        return jpaUserRepository.findByIdOrNull(id)
    }
}
