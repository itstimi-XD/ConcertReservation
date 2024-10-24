package io.hhplus.concertreservationservice.exception

class ResourceNotFoundException(
    payload: Any? = null
) : BusinessException(ErrorType.RESOURCE_NOT_FOUND, payload)
