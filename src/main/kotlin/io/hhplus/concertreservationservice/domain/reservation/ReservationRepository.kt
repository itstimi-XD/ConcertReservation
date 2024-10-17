package io.hhplus.concertreservationservice.domain.reservation

interface ReservationRepository {
    fun save(reservation: Reservation): Reservation
}