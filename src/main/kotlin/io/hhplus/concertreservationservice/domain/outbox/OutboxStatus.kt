package io.hhplus.concertreservationservice.domain.outbox

enum class OutboxStatus {
    PENDING,
    SENT,
    FAILED
}