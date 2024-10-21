package io.hhplus.concertreservationservice.domain.seat

interface SeatRepository {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: String): List<Seat>
    fun findByConcertScheduleIdAndSeatNumber(
        concertScheduleId: Long,
        seatNumber: Int
    ): Seat?
    fun save(seat: Seat): Seat
    fun findById(seatId: Long): Seat?
}