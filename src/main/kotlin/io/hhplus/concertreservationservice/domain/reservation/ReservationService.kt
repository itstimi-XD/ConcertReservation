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
    companion object {
        const val EXPIRATION_MINUTES = 5L
    }
    @Transactional
    fun reserveSeat(userId: Long, concertScheduleId: Long, seatNumber: Int): Reservation {
        try {
            // 예약 만료 시간 설정
            val expirationMinutes = EXPIRATION_MINUTES
            val now = LocalDateTime.now()
            val expirationTime = now.plusMinutes(expirationMinutes)

            // 콘서트 스케줄 확인
            concertScheduleRepository.findById(concertScheduleId)
                ?: ResourceNotFoundException("Concert schedule not found")

            // 좌석 정보 조회 (비관적 락 사용)
            val seat = seatRepository.findByConcertScheduleIdAndSeatNumberWithLock(concertScheduleId, seatNumber)
                ?: throw ResourceNotFoundException("Seat not found")

            if (seat.seatStatus != SeatStatus.AVAILABLE) {
                throw SeatAlreadyOccupiedException("seatId: ${seat.id}, seatNumber: ${seat.seatNumber}")
            }

            // 좌석 점유
            seat.occupy(userId, now)

            // 예약 생성
            val reservation = Reservation.create(
                userId = userId,
                concertScheduleId = concertScheduleId,
                seatId = seat.id,
                seatNumber = seat.seatNumber,
                expirationMinutes = expirationMinutes
            )

            // 좌석 저장 (낙관적 락 적용)
            seatRepository.save(seat)

            return reservationRepository.save(reservation)
            // TODO : 비관적 락이 적용된 것이라면 아래의 낙관락 충돌 시 예외처리는 필요 없어짐. 검토 필요.
        } catch (e: OptimisticLockingFailureException) {
            // 낙관적 락 충돌 시 예외 처리
            throw ConcurrencyException("좌석 예약 중 동시 수정이 발생했습니다. 다시 시도해 주세요.")
        }
    }

    fun findReservationByIdAndUserId(reservationId: Long, userId: Long): Reservation {
        return reservationRepository.findByIdAndUserId(reservationId, userId)
            ?: throw ResourceNotFoundException("Reservation not found")
    }

    // TODO : [생각 해보기] - @Transactional 필요 한가? 필요하다면 왜 필요한가?
    @Transactional
    fun confirmReservation(reservationId: Long, userId: Long) {
        val reservation = findReservationByIdAndUserId(reservationId, userId)
        reservation.confirmReservation()
        reservationRepository.save(reservation)
    }

    // TODO : [생각 해보기] - @Transactional 필요 한가? 필요하다면 왜 필요한가?
    @Transactional
    fun cancelReservation(reservationId: Long, userId: Long) {
        val reservation = findReservationByIdAndUserId(reservationId, userId)
        reservation.cancelReservation()
        reservationRepository.save(reservation)
    }
}
