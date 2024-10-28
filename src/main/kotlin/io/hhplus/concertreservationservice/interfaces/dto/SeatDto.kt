package io.hhplus.concertreservationservice.interfaces.dto

import io.hhplus.concertreservationservice.domain.seat.SeatStatus

data class SeatDto(
    val seatId: Long,
    val seatNumber: Int,
    val seatStatus: SeatStatus
)
