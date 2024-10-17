package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleRepository
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ReservationService(
    private val seatRepository: SeatRepository,
    private val concertScheduleRepository: ConcertScheduleRepository,
    private val reservationRepository: ReservationRepository
) {
    @Transactional
    fun reserveSeat(userId: Long, concertScheduleId: Long, seatNumber: Int): Reservation {
        try {
            // 예약 만료 시간 설정 (예: 5분 후)
            val expirationMinutes = 5L
            val now = LocalDateTime.now()
            val expirationTime = now.plusMinutes(expirationMinutes)

            // 콘서트 스케줄 확인
            val concertSchedule = concertScheduleRepository.findById(concertScheduleId)
                ?: throw IllegalArgumentException("Concert schedule not found")

            // 좌석 정보 조회
            val seat = seatRepository.findByConcertScheduleIdAndSeatNumber(concertScheduleId, seatNumber)
                ?: throw IllegalArgumentException("Seat not found")

            if (seat.seatStatus != "available") {
                throw IllegalArgumentException("Seat is already occupied")
            }

            // 좌석 상태 업데이트
            seat.seatStatus = "occupied"
            seat.userId = userId
            seat.updatedAt = now

            // 좌석 저장 (낙관적 락 적용)
            seatRepository.save(seat)

            // 예약 정보 생성
            val reservation = Reservation(
                userId = userId,
                concertScheduleId = concertScheduleId,
                seatId = seat.id,
                seatNumber = seat.seatNumber,
                status = ReservationStatus.RESERVED.value,
                createdAt = now,
                updatedAt = now,
                expirationTime = expirationTime // 만료 시간 설정
            )
            return reservationRepository.save(reservation)
        } catch (e: OptimisticLockingFailureException) {
            // 낙관적 락 충돌 시 예외 처리
            throw SeatAlreadyReservedException("Seat is already reserved by another user")
        }
    }
}
