package io.hhplus.concertreservationservice.interfaces.api.balance

import io.hhplus.concertreservationservice.application.balance.BalanceFacade
import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.BalanceRequest
import io.hhplus.concertreservationservice.interfaces.dto.BalanceResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/balance")
class BalanceController(
    private val balanceFacade: BalanceFacade
) {
    @PostMapping("/recharge")
    fun rechargeBalance(
        @RequestHeader("User-Token") userToken: String,
        @RequestHeader("Queue-Token") queueToken: String,
        @RequestBody balanceRequest: BalanceRequest
    ): ResponseEntity<ApiResponse> {
        balanceFacade.rechargeBalance(userToken, queueToken, balanceRequest)
        return ResponseEntity.ok(ApiResponse("Balance recharged successfully"))
    }

    @GetMapping("/inquiry")
    fun getBalance(
        @RequestHeader("User-Token") userToken: String,
        @RequestHeader("Queue-Token") queueToken: String
    ): ResponseEntity<BalanceResponse> {
        val response = balanceFacade.getBalance(userToken, queueToken)
        return ResponseEntity.ok(response)
    }
}
