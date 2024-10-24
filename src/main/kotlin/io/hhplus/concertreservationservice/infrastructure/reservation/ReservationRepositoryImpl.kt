package io.hhplus.concertreservationservice.infrastructure.reservation

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.domain.reservation.ReservationRepository
import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReservationRepositoryImpl(
    private val jpaReservationRepository: JpaReservationRepository
) : ReservationRepository {

    override fun save(reservation: Reservation): Reservation {
        return jpaReservationRepository.save(reservation)
    }

    override fun findAllByStatusAndExpirationTimeBefore(status: ReservationStatus, expirationTime: LocalDateTime): List<Reservation> {
        return jpaReservationRepository.findAllByStatusAndExpirationTimeBefore(status, expirationTime)
    }

    override fun findByIdAndUserId(id: Long, userId: Long): Reservation? {
        return jpaReservationRepository.findByIdAndUserId(id, userId)
    }
}
