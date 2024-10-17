package io.hhplus.concertreservationservice.interfaces.api.concert

import io.hhplus.concertreservationservice.application.concert.ConcertFacade
import io.hhplus.concertreservationservice.interfaces.dto.ConcertScheduleDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/concerts")
class ConcertController(
    private val concertFacade: ConcertFacade
) {
    @GetMapping("/available-schedules")
    fun getAvailableConcertSchedules(
        @RequestHeader("User-Token") userToken: String,
        @RequestHeader("Queue-Token") queueToken: String
    ): ResponseEntity<List<ConcertScheduleDto>> {

        val schedules = concertFacade.getAvailableConcertSchedules(userToken, queueToken)
        return ResponseEntity.ok(schedules)
    }
}
