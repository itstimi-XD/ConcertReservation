package io.hhplus.concertreservationservice.domain.reservation

import java.time.LocalDateTime

interface ReservationRepository {
    fun save(reservation: Reservation): Reservation
    fun findAllByStatusAndExpirationTimeBefore(status: String, expirationTime: LocalDateTime): List<Reservation>
    fun findByIdAndUserId(id: Long, userId: Long): Reservation?
}