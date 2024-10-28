package io.hhplus.concertreservationservice.interfaces.api.reservation

import io.hhplus.concertreservationservice.application.concert.ConcertFacade
import io.hhplus.concertreservationservice.interfaces.dto.*
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/reservations")
class ReservationController(
    private val concertFacade: ConcertFacade
) {

    @Operation(summary = "예약 가능한 날짜 목록 조회", description = "예약 가능한 날짜 목록 조회 API")
    @GetMapping("/available-dates")
    fun getAvailableConcertSchedules(
        request: HttpServletRequest
    ): ResponseEntity<List<ConcertScheduleDto>> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        val schedules = concertFacade.getAvailableConcertSchedules(userToken, queueToken)
        return ResponseEntity.ok(schedules)
    }

    @Operation(summary = "예약 가능한 좌석 목록 조회", description = "예약 가능한 좌석 목록 조회 API")
    @GetMapping("/available-seats")
    fun getAvailableSeats(
        request: HttpServletRequest,
        @RequestParam("concertScheduleId") concertScheduleId: Long
    ): ResponseEntity<List<SeatDto>> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        val seats = concertFacade.getAvailableSeats(userToken, queueToken, concertScheduleId)
        return ResponseEntity.ok(seats)
    }

    @Operation(summary = "좌석 예약", description = "좌석 예약 API")
    @PostMapping("/reserve")
    fun reserveSeat(
        request: HttpServletRequest,
        @RequestBody reservationRequest: ReservationRequest
    ): ResponseEntity<ReservationResponse> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        val response = concertFacade.reserveSeat(userToken, queueToken, reservationRequest)
        return ResponseEntity.ok(response)
    }
}