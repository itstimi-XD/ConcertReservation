package io.hhplus.concertreservationservice.interfaces.dto

data class SeatAvailabilityResponse(
    val date: String,  // 조회한 날짜 (yyyy-MM-dd 형식)
    val availableSeats: List<Int>  // 예약 가능한 좌석 번호 목록
)
