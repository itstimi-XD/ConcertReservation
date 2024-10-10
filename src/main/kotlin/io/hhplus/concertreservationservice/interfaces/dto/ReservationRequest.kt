package io.hhplus.concertreservationservice.interfaces.dto

data class ReservationRequest(
    val userId: Long,      // 유저 ID
    val date: String,      // 예약 날짜
    val seatNumber: Int    // 예약할 좌석 번호
)
