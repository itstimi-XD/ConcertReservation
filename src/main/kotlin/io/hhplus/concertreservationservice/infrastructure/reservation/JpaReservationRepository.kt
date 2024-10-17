package io.hhplus.concertreservationservice.infrastructure.reservation

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface JpaReservationRepository : JpaRepository<Reservation, Long> {
    fun findAllByStatusAndExpirationTimeBefore(status: String, expirationTime: LocalDateTime): List<Reservation>
    fun findByIdAndUserId(id: Long, userId: Long): Reservation?
}