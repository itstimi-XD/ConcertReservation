package io.hhplus.concertreservationservice.domain.payment

interface PaymentRepository {
    fun findByIdAndUserId(id: Long, userId: Long): Payment?
    fun save(payment: Payment): Payment
}