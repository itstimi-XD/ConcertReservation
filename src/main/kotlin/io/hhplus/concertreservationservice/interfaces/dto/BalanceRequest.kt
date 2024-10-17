package io.hhplus.concertreservationservice.interfaces.dto

data class BalanceRequest(
    val amount: Int
)

data class BalanceResponse(
    val balance: Int
)
