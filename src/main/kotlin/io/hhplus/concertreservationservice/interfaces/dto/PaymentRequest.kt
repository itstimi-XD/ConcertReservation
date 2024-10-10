package io.hhplus.concertreservationservice.interfaces.dto

data class PaymentRequest(
    val userId: Long,       // 유저 ID
    val reservationId: Long, // 예약 ID
    val amount: Double      // 결제 금액
)
