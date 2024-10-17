package io.hhplus.concertreservationservice.interfaces.api.user

import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.ChargeRequest
import io.hhplus.concertreservationservice.interfaces.dto.TokenResponse
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController {

//    @Operation(summary = "유저 토큰 발급", description = "대기열에서 유저 토큰을 발급받는 API")
//    @PostMapping("/token")
//    fun issueToken(): ResponseEntity<TokenResponse> {
//
//        // TODO : 토큰 발급 로직 구현
//        val mockResponse = TokenResponse(
//            token = "mocked-token-uuid",
//            queuePosition = 1,  // 대기열 순서
//            estimatedWaitTime = 60  // 대기 예상 시간 (초 단위)
//        )
//        return ResponseEntity.ok(mockResponse)
//    }

    @Operation(summary = "대기열 상태 조회", description = "유저의 대기열 상태를 조회하는 API")
    @GetMapping("/{userToken}/queue-status")
    fun getQueueStatus(@PathVariable userToken: String): ResponseEntity<TokenResponse> {
        // TODO: 대기열 상태 조회 로직 구현
        val mockQueueResponse = TokenResponse(
            token = userToken,
            queuePosition = 1,  // 대기열 순서
            estimatedWaitTime = 30  // 대기 예상 시간 (초 단위)
        )
        return ResponseEntity.ok(mockQueueResponse)
    }
    @Operation(summary = "사용자 잔액 조회", description = "사용자의 잔액을 조회하는 API")
    @GetMapping("/{userToken}/balance")
    fun getBalance(@PathVariable userToken: String): ResponseEntity<ApiResponse> {
        // TODO : 사용자 잔액 조회 로직 구현
        val mockBalance = ApiResponse("1000.0")
        return ResponseEntity.ok(mockBalance)
    }
    @Operation(summary = "사용자 잔액 충전", description = "사용자의 잔액을 충전하는 API")
    @PostMapping("/charge")
    fun chargeBalance(@RequestBody request: ChargeRequest): ResponseEntity<ApiResponse> {
        // TODO : 사용자 잔액 충전 로직 구현
        return ResponseEntity.ok(ApiResponse("Balance charged successfully"))
    }

}