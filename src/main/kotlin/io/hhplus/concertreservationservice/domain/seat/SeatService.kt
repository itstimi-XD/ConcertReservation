package io.hhplus.concertreservationservice.domain.seat

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.exception.ResourceNotFoundException
import io.hhplus.concertreservationservice.interfaces.dto.SeatDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
    fun getSeatById(seatId: Long): Seat {
        return seatRepository.findById(seatId)
            ?: throw ResourceNotFoundException("Seat not found")
    }
    fun getSeatPrice(seatId: Long): Int {
        val seat = getSeatById(seatId)
        return seat.price
    }
    fun findSeatByLock(concertScheduleId: Long, seatNumber: Int): Seat {
        return seatRepository.findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId, seatNumber)
            ?: throw ResourceNotFoundException("Seat not found")
    }

    fun releaseSeatByReservation(reservation: Reservation, now: LocalDateTime) {
        val seat = seatRepository.findById(reservation.seatId)
            ?: throw ResourceNotFoundException("Seat not found")
        seat.seatStatus = SeatStatus.AVAILABLE
        seat.userId = null
        seat.updatedAt = now
        seatRepository.save(seat)
    }

}