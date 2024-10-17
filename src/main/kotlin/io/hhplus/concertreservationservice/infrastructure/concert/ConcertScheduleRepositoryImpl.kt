package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class ConcertScheduleRepositoryImpl(
    private val jpaConcertScheduleRepository: JpaConcertScheduleRepository
) : ConcertScheduleRepository {

    override fun findAllByConcertDateAfter(date: LocalDate): List<ConcertSchedule> {
        return jpaConcertScheduleRepository.findAllByConcertDateAfter(date)
    }
    override fun findByAvailableDateAfter(availableDate: LocalDateTime): List<ConcertSchedule> {
        return jpaConcertScheduleRepository.findByAvailableDateAfter(availableDate)
    }

    override fun findById(id: Long): ConcertSchedule? {
        return jpaConcertScheduleRepository.findById(id).orElse(null)
    }
}
