package io.hhplus.concertreservationservice.domain.concert

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConcertScheduleService(
    private val concertScheduleRepository: ConcertScheduleRepository
) {
    fun findAllActive(): List<ConcertSchedule> {
        return concertScheduleRepository.findAllByConcertDateAfter(LocalDate.now())
    }
}
