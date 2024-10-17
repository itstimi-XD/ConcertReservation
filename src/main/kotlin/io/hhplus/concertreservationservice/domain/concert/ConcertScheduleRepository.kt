package io.hhplus.concertreservationservice.domain.concert

import java.time.LocalDate

interface ConcertScheduleRepository {
    fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule>
}