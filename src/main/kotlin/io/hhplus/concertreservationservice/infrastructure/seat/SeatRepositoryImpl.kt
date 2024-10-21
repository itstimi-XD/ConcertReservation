package io.hhplus.concertreservationservice.infrastructure.seat

import io.hhplus.concertreservationservice.domain.seat.Seat
import io.hhplus.concertreservationservice.domain.seat.SeatRepository
import org.springframework.stereotype.Component

@Component
class SeatRepositoryImpl(
    private val jpaSeatRepository: JpaSeatRepository
) : SeatRepository {
    override fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String) =
        jpaSeatRepository.findByConcertScheduleIdAndSeatStatus(concertScheduleId, seatStatus)

    override fun findByConcertScheduleIdAndSeatNumber(concertScheduleId: Long, seatNumber: Int) =
        jpaSeatRepository.findByConcertScheduleIdAndSeatNumber(concertScheduleId, seatNumber)

    override fun save(seat: Seat): Seat {
        return jpaSeatRepository.save(seat)
    }
    override fun findById(seatId: Long): Seat? {
        return jpaSeatRepository.findById(seatId).orElse(null)
    }
}