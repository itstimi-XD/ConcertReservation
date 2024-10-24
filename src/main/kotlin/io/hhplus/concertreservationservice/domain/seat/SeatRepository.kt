package io.hhplus.concertreservationservice.domain.seat

interface SeatRepository {
    fun findByConcertScheduleIdAndSeatStatus(concertScheduleId: Long, seatStatus: SeatStatus): List<Seat>
    fun findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId: Long, seatNumber: Int): Seat?
    fun save(seat: Seat): Seat
    fun findById(seatId: Long): Seat?
    fun saveAll(seats: List<Seat>): List<Seat>
}