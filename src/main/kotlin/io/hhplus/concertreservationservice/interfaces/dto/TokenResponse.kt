package io.hhplus.concertreservationservice.interfaces.dto

data class TokenResponse(
    val token: String,   // 발급된 토큰
    val expirationTime: Long // 토큰 만료 시간
)
