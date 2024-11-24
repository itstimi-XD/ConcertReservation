package io.hhplus.concertreservationservice.domain.payment

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.concertreservationservice.domain.event.PaymentCompletedEvent
import io.hhplus.concertreservationservice.domain.outbox.OutboxEvent
import io.hhplus.concertreservationservice.domain.outbox.OutboxStatus
import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.exception.ResourceNotFoundException
import io.hhplus.concertreservationservice.infrastructure.outbox.OutboxEventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class PaymentService(
    private val userRepository: UserRepository,
    private val paymentRepository: PaymentRepository,
    private val outboxEventRepository: OutboxEventRepository,
    private val objectMapper: ObjectMapper

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

        // 결제 완료 이벤트 생성
        val event = PaymentCompletedEvent(
            paymentId = payment.id,
            userId = userId,
            amount = seatPrice.toLong(),
            reservationId = reservationId,
            timestamp = LocalDateTime.now(ZoneOffset.UTC)
        )

        // 이벤트를 JSON 문자열로 변환
        val payload = objectMapper.writeValueAsString(event)

        // OutboxEvent 생성
        val outboxEvent = OutboxEvent(
            eventType = "PAYMENT_COMPLETED",
            payload = payload,
            status = OutboxStatus.PENDING
        )

        // OutboxEvent 저장
        outboxEventRepository.save(outboxEvent)

        return payment
    }
}
