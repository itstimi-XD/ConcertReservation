package io.hhplus.concertreservationservice.infrastructure.payment

import io.hhplus.concertreservationservice.domain.payment.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaPaymentRepository : JpaRepository<Payment, Long> {
    fun findByReservationId(reservationId: Long): Payment?

    // 추가: 사용자 ID와 결제 ID로 결제 정보를 조회하는 메서드
    fun findByIdAndUserId(id: Long, userId: Long): Payment?
}