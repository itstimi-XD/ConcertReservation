package io.hhplus.concertreservationservice.domain.reservation

interface SeatRepository {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String): List<Seat>
    fun findByConcertScheduleIdAndSeatNumber(
        concertScheduleId: Long,
        seatNumber: Int
    ): Seat?
    fun save(seat: Seat): Seat
}