package io.hhplus.concertreservationservice.exception

class QueueException(
    errorType: ErrorType,
    payload: Any? = null
) : BusinessException(errorType, payload)