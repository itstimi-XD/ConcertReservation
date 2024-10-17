package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface JpaConcertScheduleRepository {
    fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule>
}