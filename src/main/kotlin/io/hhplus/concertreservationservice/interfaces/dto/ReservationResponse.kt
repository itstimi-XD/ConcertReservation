package io.hhplus.concertreservationservice.interfaces.dto

import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus

data class ReservationResponse(
    val reservationId: Long,
    val status: ReservationStatus,
    val seatNumber: Int,
    val concertScheduleId: Long
)