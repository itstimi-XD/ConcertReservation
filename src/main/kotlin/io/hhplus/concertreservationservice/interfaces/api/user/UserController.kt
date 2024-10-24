package io.hhplus.concertreservationservice.interfaces.api.user

import io.hhplus.concertreservationservice.application.queue.QueueFacade
import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.ChargeRequest
import io.hhplus.concertreservationservice.interfaces.dto.QueueTokenResponse
import io.hhplus.concertreservationservice.interfaces.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
        private val queueFacade: QueueFacade
        ) {

    @Operation(summary = "대기열 상태 조회", description = "유저의 대기열 상태를 조회하는 API")
    @GetMapping("/queue-status")
    fun getQueueStatus(request: HttpServletRequest): ResponseEntity<QueueTokenResponse> {
        val queueToken = request.getAttribute("queueToken") as String
        val  queueStatus = queueFacade.getQueueStatus(queueToken)
        return ResponseEntity.ok(queueStatus)
    }

    @Operation(summary = "사용자 잔액 조회", description = "사용자의 잔액을 조회하는 API")
    @GetMapping("/balance")
    fun getBalance(request: HttpServletRequest): ResponseEntity<ApiResponse> {
        val userToken = request.getAttribute("userToken") as String
        val mockBalance = ApiResponse("1000.0")
        return ResponseEntity.ok(mockBalance)
    }

    @Operation(summary = "사용자 잔액 충전", description = "사용자의 잔액을 충전하는 API")
    @PostMapping("/charge")
    fun chargeBalance(request: HttpServletRequest, @RequestBody chargeRequest: ChargeRequest): ResponseEntity<ApiResponse> {
        val userToken = request.getAttribute("userToken") as String
        return ResponseEntity.ok(ApiResponse("Balance charged successfully"))
    }
}