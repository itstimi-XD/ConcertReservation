package io.hhplus.concertreservationservice.domain.reservation

interface SeatRepository {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String): List<Seat>
}