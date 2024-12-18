package io.hhplus.concertreservationservice.infrastructure.seat

import io.hhplus.concertreservationservice.domain.seat.Seat
import io.hhplus.concertreservationservice.domain.seat.SeatRepository
import io.hhplus.concertreservationservice.domain.seat.SeatStatus
import org.springframework.stereotype.Component

@Component
class SeatRepositoryImpl(
    private val jpaSeatRepository: JpaSeatRepository
) : SeatRepository {
    override fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: SeatStatus): List<Seat> =
        jpaSeatRepository.findByConcertScheduleIdAndSeatStatus(concertScheduleId, seatStatus)

    override fun findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId: Long, seatNumber: Int): Seat? =
        jpaSeatRepository.findByConcertScheduleIdAndSeatNumber(concertScheduleId, seatNumber)

    override fun save(seat: Seat): Seat {
        return jpaSeatRepository.save(seat)
    }

    override fun findById(seatId: Long): Seat? {
        return jpaSeatRepository.findById(seatId).orElse(null)
    }

    override fun saveAll(seats: List<Seat>): List<Seat> {
        return jpaSeatRepository.saveAll(seats)
    }
}