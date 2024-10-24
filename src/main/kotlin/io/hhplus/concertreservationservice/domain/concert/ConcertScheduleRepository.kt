package io.hhplus.concertreservationservice.domain.concert

import java.time.LocalDate
import java.time.LocalDateTime

interface ConcertScheduleRepository {
    fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule>
    fun findByAvailableDateAfter(availableDate: LocalDateTime): List<ConcertSchedule>
    fun findById(id: Long): ConcertSchedule?
    fun save(concertSchedule: ConcertSchedule): ConcertSchedule
}