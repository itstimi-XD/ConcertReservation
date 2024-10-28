package io.hhplus.concertreservationservice.exception

class ConcurrencyException(
    payload: Any? = null
) : BusinessException(ErrorType.CONCURRENCY_ERROR, payload)
