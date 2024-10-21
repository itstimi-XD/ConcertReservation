package io.hhplus.concertreservationservice.infrastructure.payment

import io.hhplus.concertreservationservice.domain.payment.Payment
import io.hhplus.concertreservationservice.domain.payment.PaymentRepository
import org.springframework.stereotype.Component

@Component
class PaymentRepositoryImpl(
    private val jpaPaymentRepository: JpaPaymentRepository
) : PaymentRepository {

    override fun findByIdAndUserId(id: Long, userId: Long): Payment? {
        return jpaPaymentRepository.findByIdAndUserId(id, userId)
    }

    override fun save(payment: Payment): Payment {
        return jpaPaymentRepository.save(payment)
    }
}