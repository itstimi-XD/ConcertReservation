package io.hhplus.concertreservationservice.infrastructure.reservation

import io.hhplus.concertreservationservice.domain.reservation.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeatRepository : JpaRepository<Seat, Long> {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String): List<Seat>
}
