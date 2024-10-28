package io.hhplus.concertreservationservice.interfaces.dto

import io.hhplus.concertreservationservice.domain.payment.PaymentStatus

data class PaymentResponse(
    val paymentId: Long?,
    val status: PaymentStatus,
    val amount: Int,
    val reservationId: Long
)