package io.hhplus.concertreservationservice.domain.payment

import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import io.hhplus.concertreservationservice.domain.reservation.ReservationRepository
import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus
import io.hhplus.concertreservationservice.domain.reservation.SeatRepository
import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.domain.user.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PaymentService(
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository,
    private val queueRepository: QueueRepository,
    private val seatRepository: SeatRepository
) {
    @Transactional
    fun makePayment(userId: Long, reservationId: Long): Payment {
        val now = LocalDateTime.now()

        // 예약 정보 확인
        val reservation = reservationRepository.findByIdAndUserId(reservationId, userId)
            ?: throw IllegalArgumentException("Reservation not found")

        // 예약 만료 시간 확인
        if (reservation.expirationTime.isBefore(now)) {
            // 대기열 삭제
            queueRepository.deleteByUserId(userId)

            // 좌석 점유 해제
            val seat = reservation.seatId.let { seatRepository.findById(it) }
                ?: throw IllegalArgumentException("Seat not found")
            seat.seatStatus = "available"
            seat.userId = null
            seat.updatedAt = now
            // 좌석 저장
            seatRepository.save(seat)

            // 예약 상태 업데이트
            reservation.status = ReservationStatus.CANCELLED.value
            reservation.updatedAt = now
            reservationRepository.save(reservation)

            throw IllegalArgumentException("Reservation time over")
        }

        // 사용자 정보 락 처리
        val user = userRepository.findByIdForUpdate(userId)
            ?: throw IllegalArgumentException("User not found")

        val seatPrice = 10000 // 좌석 가격, 실제로는 좌석 또는 예약 정보에서 가져와야 함

        // 잔액 확인
        if (user.balance < seatPrice) {
            throw IllegalArgumentException("Insufficient balance")
        }

        // 잔액 차감
        user.balance -= seatPrice
        userRepository.save(user)

        // 결제 정보 생성
        val payment = Payment(
            userId = userId,
            reservationId = reservationId,
            amount = seatPrice,
            status = "completed",
            createdAt = now,
            updatedAt = now
        )
        paymentRepository.save(payment)

        // 예약 상태 업데이트
        reservation.status = ReservationStatus.PAYMENT_COMPLETED.value
        reservation.updatedAt = now
        reservationRepository.save(reservation)

        return payment
    }
}
