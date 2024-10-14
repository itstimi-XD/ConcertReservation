package io.hhplus.concertreservationservice.interfaces.api.payment

import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.PaymentRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payments")
class PaymentController {

    @PostMapping("/pay")
    fun makePayment(@RequestBody request: PaymentRequest): ResponseEntity<ApiResponse> {
        // TODO : 결제 로직 구현
        return ResponseEntity.ok(ApiResponse("Payment completed successfully"))
    }
}