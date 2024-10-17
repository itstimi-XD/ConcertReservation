package io.hhplus.concertreservationservice.interfaces.dto

data class SeatDto(
    val seatId: Long,
    val seatNumber: Int,
    val seatStatus: String // "occupied", "available"
)
