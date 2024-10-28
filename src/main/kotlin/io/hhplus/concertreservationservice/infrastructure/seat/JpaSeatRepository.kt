package io.hhplus.concertreservationservice.infrastructure.seat

import io.hhplus.concertreservationservice.domain.seat.Seat
import io.hhplus.concertreservationservice.domain.seat.SeatStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeatRepository : JpaRepository<Seat, Long> {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: SeatStatus): List<Seat>
    fun findByConcertScheduleIdAndSeatNumber(concertScheduleId: Long, seatNumber: Int): Seat?
}