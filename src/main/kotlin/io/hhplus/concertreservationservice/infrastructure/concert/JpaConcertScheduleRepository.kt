package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface JpaConcertScheduleRepository: JpaRepository<ConcertSchedule, Long> {
    fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule>
    fun findByAvailableDateAfter(availableDate: LocalDateTime): List<ConcertSchedule>
}