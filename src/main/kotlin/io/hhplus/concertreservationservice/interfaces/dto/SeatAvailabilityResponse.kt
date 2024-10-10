package io.hhplus.concertreservationservice.interfaces.dto

data class SeatAvailabilityResponse(
    val date: String,           // 예약 가능한 날짜
    val availableSeats: List<Int>  // 예약 가능한 좌석 번호 리스트
)
