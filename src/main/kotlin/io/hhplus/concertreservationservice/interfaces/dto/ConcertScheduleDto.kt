package io.hhplus.concertreservationservice.interfaces.dto

import java.time.LocalDateTime

data class ConcertScheduleDto(
    val concertId: Long,
    val concertTitle: String,
    val concertDescription: String,
    val scheduleId: Long,
    val concertDate: LocalDateTime,
    val availableDate: LocalDateTime,
    val totalSeats: Int,
    val remainingSeats: Int
)
