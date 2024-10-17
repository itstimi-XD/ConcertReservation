package io.hhplus.concertreservationservice.application.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertService
import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.ConcertScheduleDto
import org.springframework.stereotype.Service

@Service
class ConcertFacade(
    private val concertService: ConcertService,
    private val authUtil: AuthUtil
) {
    fun getAvailableConcertSchedules(userToken: String, queueToken: String): List<ConcertScheduleDto> {
        authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        return concertService.getAvailableConcertSchedules()
    }
}