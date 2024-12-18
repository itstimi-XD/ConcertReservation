package io.hhplus.concertreservationservice.interfaces.api.payment

import io.hhplus.concertreservationservice.application.payment.PaymentFacade
import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.PaymentRequest
import io.hhplus.concertreservationservice.interfaces.dto.PaymentResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payments")
class PaymentController(
    private val paymentFacade: PaymentFacade
) {
    @Operation(summary = "결제", description = "결제 API")
    @PostMapping("/pay")
    fun makePayment(
        request: HttpServletRequest,
        @RequestBody paymentRequest: PaymentRequest
    ): ResponseEntity<PaymentResponse> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        val response = paymentFacade.makePayment(userToken, queueToken, paymentRequest)
        return ResponseEntity.ok(response)
    }
}