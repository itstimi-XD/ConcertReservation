package io.hhplus.concertreservationservice.infrastructure.reservation

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.domain.reservation.ReservationRepository
import org.springframework.stereotype.Component

@Component
class ReservationRepositoryImpl(
    private val jpaReservationRepository: JpaReservationRepository
): ReservationRepository {
    override fun save(reservation: Reservation) = jpaReservationRepository.save(reservation)
}