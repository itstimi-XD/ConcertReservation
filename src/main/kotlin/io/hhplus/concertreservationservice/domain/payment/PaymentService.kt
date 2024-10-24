package io.hhplus.concertreservationservice.domain.payment

import io.hhplus.concertreservationservice.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PaymentService(
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository
) {

    @Transactional
    fun makePayment(userId: Long, reservationId: Long, seatPrice: Int): Payment {
        val now = LocalDateTime.now()

        // 사용자 정보 락 처리
        val user = userRepository.findByIdForUpdate(userId)
            ?: throw IllegalArgumentException("User not found")

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
            status = PaymentStatus.COMPLETED,
            createdAt = now,
            updatedAt = now
        )
        paymentRepository.save(payment)
        return payment
    }
}
