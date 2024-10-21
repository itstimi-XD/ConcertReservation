package io.hhplus.concertreservationservice.infrastructure.seat

import io.hhplus.concertreservationservice.domain.seat.Seat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaSeatRepository : JpaRepository<Seat, Long> {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String): List<Seat>
    fun findByConcertScheduleIdAndSeatNumber(
        concertScheduleId: Long,
        seatNumber: Int
    ): Seat?
}
