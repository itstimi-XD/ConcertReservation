package io.hhplus.concertreservationservice.domain.reservation

enum class ReservationStatus(val value: String) {
    RESERVED("reserved"),
    PAYMENT_COMPLETED("payment_completed"),
    CANCELLED("cancelled")
}
