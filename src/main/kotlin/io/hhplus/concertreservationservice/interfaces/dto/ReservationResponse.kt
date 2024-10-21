package io.hhplus.concertreservationservice.interfaces.dto

data class ReservationResponse(
    val reservationId: Long,
    val status: String,
    val seatNumber: Int,
    val concertScheduleId: Long
)