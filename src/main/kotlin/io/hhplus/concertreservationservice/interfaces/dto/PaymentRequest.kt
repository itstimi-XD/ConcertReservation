package io.hhplus.concertreservationservice.interfaces.dto

data class PaymentRequest(
    val userToken: String,  // 사용자 토큰
    val reservationId: Long, // 예약 ID
    val amount: Double      // 결제 금액
)
