package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ConcertScheduleRepositoryImpl(
    private val jpaConcertScheduleRepository: JpaConcertScheduleRepository
) : ConcertScheduleRepository {

    override fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule> {
        return jpaConcertScheduleRepository.findAllByConcertDateAfter(date)
    }
}
