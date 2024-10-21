package io.hhplus.concertreservationservice.application.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertService
import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.domain.reservation.ReservationService
import io.hhplus.concertreservationservice.domain.reservation.SeatService
import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.ConcertScheduleDto
import io.hhplus.concertreservationservice.interfaces.dto.ReservationRequest
import io.hhplus.concertreservationservice.interfaces.dto.ReservationResponse
import io.hhplus.concertreservationservice.interfaces.dto.SeatDto
import org.springframework.stereotype.Service

@Service
class ConcertFacade(
    private val concertService: ConcertService,
    private val seatService: SeatService,
    private val reservationService: ReservationService,
    private val authUtil: AuthUtil
) {
    fun getAvailableConcertSchedules(userToken: String, queueToken: String): List<ConcertScheduleDto> {
        authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        return concertService.getAvailableConcertSchedules()
    }

    fun getAvailableSeats(userToken: String, queueToken: String, concertScheduleId: Long): List<SeatDto> {
        authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        return seatService.getAvailableSeats(concertScheduleId)
    }

    fun reserveSeat(userToken: String, queueToken: String, request: ReservationRequest): ReservationResponse {
        val userId = authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        val reservation = reservationService.reserveSeat(userId, request.concertScheduleId, request.seatNumber)
        return ReservationResponse(
            reservationId = reservation.id,
            status = reservation.status,
            seatNumber = reservation.seatNumber,
            concertScheduleId = reservation.concertScheduleId
        )
    }
}