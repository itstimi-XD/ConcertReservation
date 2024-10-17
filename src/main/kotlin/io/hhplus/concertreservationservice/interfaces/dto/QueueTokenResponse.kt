package io.hhplus.concertreservationservice.interfaces.dto

data class QueueTokenResponse(
    val queueToken: String,
    val queuePosition: Int,
    val estimatedWaitTime: Long
)
