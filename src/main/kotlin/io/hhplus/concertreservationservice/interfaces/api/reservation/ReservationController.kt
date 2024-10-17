package io.hhplus.concertreservationservice.interfaces.api.reservation

import io.hhplus.concertreservationservice.application.concert.ConcertFacade
import io.hhplus.concertreservationservice.interfaces.dto.*
import io.swagger.v3.oas.annotations.Operation
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
        @RequestHeader("User-Token") userToken: String,
        @RequestHeader("Queue-Token") queueToken: String
    ): ResponseEntity<List<ConcertScheduleDto>> {

        val schedules = concertFacade.getAvailableConcertSchedules(userToken, queueToken)
        return ResponseEntity.ok(schedules)
    }

    @Operation(summary = "예약 가능한 좌석 목록 조회", description = "예약 가능한 좌석 목록 조회 API")
    @GetMapping("/available-seats")
    fun getAvailableSeats(
        @RequestHeader("User-Token") userToken: String,
        @RequestHeader("Queue-Token") queueToken: String,
        @RequestParam("concertScheduleId") concertScheduleId: Long
    ): ResponseEntity<List<SeatDto>> {
        val seats = concertFacade.getAvailableSeats(userToken, queueToken, concertScheduleId)
        return ResponseEntity.ok(seats)
    }

    @Operation(summary = "좌석 예약", description = "좌석 예약 API")
    @PostMapping("/reserve")
    fun reserveSeat(@RequestBody request: ReservationRequest): ResponseEntity<ApiResponse> {
        // TODO : 좌석 예약 로직 구현
        // 더미 응답 반환
        return ResponseEntity.ok(ApiResponse("Seat reserved successfully"))
    }
}