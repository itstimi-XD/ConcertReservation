package io.hhplus.concertreservationservice.exception

class SeatAlreadyOccupiedException(
    payload: Any? = null
) : BusinessException(ErrorType.SEAT_ALREADY_OCCUPIED, payload)
