package io.hhplus.concertreservationservice.infrastructure.user

import io.hhplus.concertreservationservice.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import jakarta.persistence.LockModeType
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface JpaUserRepository : JpaRepository<User, Long> {

    fun findByName(username: String): User?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    fun findByIdForUpdate(@Param("userId") userId: Long): User?

    // JpaRepository에서 제공하는 메서드:
    // - findById(id: Long): Optional<User>
    // - save(entity: User): User
    // 따라서 별도로 선언하지 않아도 됩니다.
}
