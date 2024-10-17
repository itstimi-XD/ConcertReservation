package io.hhplus.concertreservationservice.infrastructure.reservation

import io.hhplus.concertreservationservice.domain.reservation.SeatRepository
import org.springframework.stereotype.Component

@Component
class SeatRepositoryImpl(
    private val jpaSeatRepository: JpaSeatRepository
) : SeatRepository {
    override fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String) =
        jpaSeatRepository.findByConcertScheduleIdAndSeatStatus(concertScheduleId, seatStatus)

    override fun findByConcertScheduleIdAndSeatNumber(concertScheduleId: Long, seatNumber: Int) =
        jpaSeatRepository.findByConcertScheduleIdAndSeatNumber(concertScheduleId, seatNumber)
}