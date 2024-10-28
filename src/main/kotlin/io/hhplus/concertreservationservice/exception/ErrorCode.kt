package io.hhplus.concertreservationservice.exception

enum class ErrorCode {
    BUSINESS_ERROR,
    NOT_FOUND,
    CLIENT_ERROR,
    DATABASE_ERROR,
    INVALID_REQUEST,
    SEAT_ERROR,
    CONCURRENCY_ERROR,
    AUTHENTICATION_ERROR
}
