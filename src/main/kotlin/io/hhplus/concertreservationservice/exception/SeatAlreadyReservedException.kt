package io.hhplus.concertreservationservice.exception

class SeatAlreadyReservedException(
    payload: Any? = null
) : BusinessException(ErrorType.SEAT_ALREADY_RESERVED, payload)