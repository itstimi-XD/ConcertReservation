package io.hhplus.concertreservationservice.interfaces.api.reservation

import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.ReservationRequest
import io.hhplus.concertreservationservice.interfaces.dto.SeatAvailabilityResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/reservations")
class ReservationController {

    @GetMapping("/available-dates")
    fun getAvailableDates(): ResponseEntity<List<LocalDate>>{
        // TODO : 예약 가능한 날짜 목록 조회 로직 구현
        // 더미 응답 반환
        val mockDates = listOf(
            LocalDate.of(2024, 10, 12),
            LocalDate.of(2024, 10, 13),
            LocalDate.of(2024, 10, 14)
        )
        return ResponseEntity.ok(mockDates)
    }

    @GetMapping("/available-seats")
    fun getAvailableSeats(@RequestParam date: LocalDate): ResponseEntity<SeatAvailabilityResponse>{
        // TODO : 예약 가능한 좌석 목록 조회 로직 구현
        // 더미 응답 반환
        val mockSeats = SeatAvailabilityResponse(
            date = date.toString(),
            availableSeats = listOf(1, 2, 3, 4, 5)
        )
        return ResponseEntity.ok(mockSeats)
    }

    @PostMapping("/reserve")
    fun reserveSeat(@RequestBody request: ReservationRequest): ResponseEntity<ApiResponse> {
        // TODO : 좌석 예약 로직 구현
        // 더미 응답 반환
        return ResponseEntity.ok(ApiResponse("Seat reserved successfully"))
    }
}