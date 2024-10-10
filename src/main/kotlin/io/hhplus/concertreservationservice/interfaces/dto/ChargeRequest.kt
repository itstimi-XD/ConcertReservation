package io.hhplus.concertreservationservice.interfaces.dto

data class ChargeRequest(
    val userId: Long,   // 유저 ID
    val amount: Double  // 충전 금액
)
