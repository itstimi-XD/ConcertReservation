package io.hhplus.concertreservationservice.exception

class InvalidTokenException(
    payload: Any? = null
) : BusinessException(ErrorType.INVALID_TOKEN, payload)