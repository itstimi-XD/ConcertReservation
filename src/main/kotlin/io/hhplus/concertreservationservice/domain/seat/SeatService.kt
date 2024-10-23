package io.hhplus.concertreservationservice.domain.seat

import io.hhplus.concertreservationservice.interfaces.dto.SeatDto
import org.springframework.stereotype.Service

@Service
class SeatService(
    private val seatRepository: SeatRepository
) {
    fun getAvailableSeats(concertScheduleId: Long): List<SeatDto> {
        val seats = seatRepository.findByConcertScheduleIdAndSeatStatus(concertScheduleId, SeatStatus.AVAILABLE)
        return seats.map { seat ->
            SeatDto(
                seatId = seat.id,
                seatNumber = seat.seatNumber,
                seatStatus = seat.seatStatus
            )
        }
    }
}
