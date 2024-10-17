package io.hhplus.concertreservationservice.application.scheduler

import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import io.hhplus.concertreservationservice.domain.reservation.ReservationRepository
import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus
import io.hhplus.concertreservationservice.domain.reservation.SeatRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ReservationCleanupScheduler(
    private val reservationRepository: ReservationRepository,
    private val seatRepository: SeatRepository,
    private val queueRepository: QueueRepository,
    @Value("\${scheduler.reservationCleanup.fixedRate}") private val fixedRate: Long
) {

    @Scheduled(fixedRateString = "\${scheduler.reservationCleanup.fixedRate}")
    fun cleanUpExpiredReservations() {
        val now = LocalDateTime.now()
        val expiredReservations = reservationRepository.findAllByStatusAndExpirationTimeBefore(
            ReservationStatus.RESERVED.value,
            now
        )

        expiredReservations.forEach { reservation ->
            // 1) 대기열에서 사용자 제거
            queueRepository.deleteByUserId(reservation.userId)

            // 2) 좌석 점유 해제
            val seat = seatRepository.findById(reservation.seatId).orElse(null)
            seat?.let {
                it.seatStatus = "available"
                it.userId = null
                seatRepository.save(it)
            }

            // 3) 예약 상태 업데이트
            reservation.status = ReservationStatus.CANCELLED.value
            reservation.updatedAt = now
            reservationRepository.save(reservation)
        }
    }
}
