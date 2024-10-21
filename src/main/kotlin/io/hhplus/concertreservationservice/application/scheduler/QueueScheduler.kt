package io.hhplus.concertreservationservice.application.scheduler

import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class QueueScheduler(
    private val queueService: QueueService,
    private val concertScheduleService: ConcertScheduleService
) {
    @Scheduled(fixedRateString = "\${scheduler.queueProcess.fixedRate}")
    fun processQueuePerConcertSchedule() {
        val activeConcertSchedules = concertScheduleService.findAllActive()
        activeConcertSchedules.forEach { schedule ->
            queueService.processNextUsersByConcertSchedule(schedule.id)
        }
    }

    @Scheduled(fixedRateString = "\${scheduler.queueCleanup.fixedRate}")
    fun cleanUpExpiredQueueEntries() {
        queueService.removeExpiredEntries()
    }
}
