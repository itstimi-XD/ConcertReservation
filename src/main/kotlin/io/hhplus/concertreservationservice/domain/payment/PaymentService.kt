package io.hhplus.concertreservationservice.domain.payment

import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentService(
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository
) {

    @Transactional
    fun makePayment(userId: Long, reservationId: Long, seatPrice: Int): Payment {

        // 사용자 정보 락 처리
        val user = userRepository.findByIdForUpdate(userId)
            ?: throw ResourceNotFoundException("User not found")

        // 잔액 차감 (User 엔티티의 메서드 호출)
        user.deductBalance(seatPrice.toDouble())
        userRepository.save(user)

        // 결제 정보 생성
        val payment = Payment.create(userId, reservationId, seatPrice)
        paymentRepository.save(payment)
        return payment
    }
}
