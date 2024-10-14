package io.hhplus.concertreservationservice.interfaces.dto

data class ChargeRequest(
    val userToken: String,
    val amount: Double  // 충전 금액
)
