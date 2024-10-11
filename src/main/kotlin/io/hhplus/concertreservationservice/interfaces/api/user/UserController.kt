package io.hhplus.concertreservationservice.interfaces.api.user

import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.ChargeRequest
import io.hhplus.concertreservationservice.interfaces.dto.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

    @PostMapping("/token")
    fun issueToken(): ResponseEntity<TokenResponse> {

        // TODO : 토큰 발급 로직 구현
        val mockResponse = TokenResponse(
            token = "mocked-token-uuid",
            queuePosition = 1,  // 대기열 순서
            estimatedWaitTime = 60  // 대기 예상 시간 (초 단위)
        )
        return ResponseEntity.ok(mockResponse)
    }

    @GetMapping("/{userToken}/balance")
    fun getBalance(@PathVariable userToken: String): ResponseEntity<ApiResponse> {
        // TODO : 사용자 잔액 조회 로직 구현
        val mockBalance = ApiResponse("1000.0")
        return ResponseEntity.ok(mockBalance)
    }

    @PostMapping("/charge")
    fun chargeBalance(@RequestBody request: ChargeRequest): ResponseEntity<ApiResponse> {
        // TODO : 사용자 잔액 충전 로직 구현
        return ResponseEntity.ok(ApiResponse("Balance charged successfully"))
    }

}