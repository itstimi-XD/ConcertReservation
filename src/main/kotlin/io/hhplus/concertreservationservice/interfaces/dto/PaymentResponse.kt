package io.hhplus.concertreservationservice.interfaces.dto

data class PaymentResponse(
    val paymentId: Long,
    val status: String,
    val amount: Int,
    val reservationId: Long
)