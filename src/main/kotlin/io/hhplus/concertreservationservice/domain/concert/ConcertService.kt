package io.hhplus.concertreservationservice.domain.concert

import io.hhplus.concertreservationservice.interfaces.dto.ConcertScheduleDto
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ConcertService(
    private val concertRepository: ConcertRepository,
    private val concertScheduleRepository: ConcertScheduleRepository
) {
    fun getAvailableConcertSchedules(): List<ConcertScheduleDto> {
        val now = LocalDateTime.now()
        val schedules = concertScheduleRepository.findByAvailableDateAfter(now)
        return schedules.map { schedule ->
            val concert = concertRepository.findById(schedule.concertId)
                ?: throw IllegalArgumentException("concert not found")

            ConcertScheduleDto(
                concertId = concert.id,
                concertTitle = concert.title,
                concertDescription = concert.description,
                scheduleId = schedule.id,
                concertDate = schedule.concertDate,
                availableDate = schedule.availableDate,
                totalSeats = schedule.totalSeats,
                remainingSeats = schedule.remainingSeats
            )
        }
    }
}
