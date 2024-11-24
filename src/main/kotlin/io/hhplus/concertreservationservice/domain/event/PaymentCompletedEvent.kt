package io.hhplus.concertreservationservice.domain.event

import java.time.LocalDateTime

data class PaymentCompletedEvent(
    val paymentId: Long,
    val userId: Long,
    val amount: Long,
    val reservationId: Long,
    val timestamp: LocalDateTime
)
