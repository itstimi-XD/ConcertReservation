package io.hhplus.concertreservationservice.exception

open class BusinessException(
    val errorType: ErrorType,
    val payload: Any? = null
) : RuntimeException(errorType.message)