package io.hhplus.concertreservationservice.application.payment

import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.PaymentRequest
import org.springframework.stereotype.Service

@Service
class PaymentFacade(
    private val paymentService: PaymentService,
    private val authUtil: AuthUtil
) {
    fun makePayment(userToken: String, queueToken: String, request: PaymentRequest): PaymentResponse {
        val userId = authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        val payment = paymentService.makePayment(userId, request.reservationId)
        return PaymentResponse(
            paymentId = payment.id,
            status = payment.status,
            amount = payment.amount,
            reservationId = payment.reservationId
        )
    }
}