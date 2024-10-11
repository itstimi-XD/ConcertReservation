package io.hhplus.concertreservationservice.interfaces.dto

data class ReservationRequest(
    val userToken: String,
    val seatNumber: Int,
    val reservationDate: String  // 예약할 날짜 (yyyy-MM-dd 형식)
)
