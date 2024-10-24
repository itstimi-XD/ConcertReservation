package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleRepository
import io.hhplus.concertreservationservice.exception.SeatAlreadyReservedException
import io.hhplus.concertreservationservice.domain.seat.SeatRepository
import io.hhplus.concertreservationservice.domain.seat.SeatStatus
import io.hhplus.concertreservationservice.exception.ConcurrencyException
import io.hhplus.concertreservationservice.exception.ResourceNotFoundException
import io.hhplus.concertreservationservice.exception.SeatAlreadyOccupiedException
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
            concertScheduleRepository.findById(concertScheduleId)
                ?: ResourceNotFoundException("Concert schedule not found")

            // 좌석 정보 조회
            val seat = seatRepository.findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId, seatNumber)
                ?: throw ResourceNotFoundException("Seat not found")

            if (seat.seatStatus != SeatStatus.AVAILABLE) {
                throw SeatAlreadyOccupiedException("seatId: ${seat.id}, seatNumber: ${seat.seatNumber}")
            }

            // 좌석 상태 업데이트
            seat.seatStatus = SeatStatus.OCCUPIED
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
                status = ReservationStatus.RESERVED,
                createdAt = now,
                updatedAt = now,
                expirationTime = expirationTime // 만료 시간 설정
            )
            return reservationRepository.save(reservation)
        } catch (e: OptimisticLockingFailureException) {
            // 낙관적 락 충돌 시 예외 처리
            throw ConcurrencyException("좌석 예약 중 동시 수정이 발생했습니다. 다시 시도해 주세요.")
        }
    }

    fun findReservationByIdAndUserId(reservationId: Long, userId: Long): Reservation {
        return reservationRepository.findByIdAndUserId(reservationId, userId)
            ?: throw ResourceNotFoundException("Reservation not found")
    }

    fun updateReservationStatus(reservation: Reservation, status: ReservationStatus, now: LocalDateTime) {
        reservation.status = status
        reservation.updatedAt = now
        reservationRepository.save(reservation)
    }
}
