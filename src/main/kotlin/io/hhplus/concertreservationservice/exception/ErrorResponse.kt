package io.hhplus.concertreservationservice.exception

data class ErrorResponse(
    val status: String,
    val errorCode: String,
    val errorMessage: String,
    val data: Any? = null
)
