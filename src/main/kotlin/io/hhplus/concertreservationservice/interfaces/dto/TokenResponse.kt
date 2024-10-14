package io.hhplus.concertreservationservice.interfaces.dto

data class TokenResponse(
    val token: String,
    val queuePosition: Int,    // 대기열 순서
    val estimatedWaitTime: Int  // 대기 예상 시간 (초 단위)
)
