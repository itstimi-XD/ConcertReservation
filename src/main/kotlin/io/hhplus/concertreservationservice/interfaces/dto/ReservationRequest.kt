package io.hhplus.concertreservationservice.interfaces.dto

data class ReservationRequest(
    val concertScheduleId: Long,
    val seatNumber: Int
)
