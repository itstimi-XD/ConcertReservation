package io.hhplus.concertreservationservice.interfaces.api.balance

import io.hhplus.concertreservationservice.application.balance.BalanceFacade
import io.hhplus.concertreservationservice.interfaces.dto.ApiResponse
import io.hhplus.concertreservationservice.interfaces.dto.BalanceRequest
import io.hhplus.concertreservationservice.interfaces.dto.BalanceResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/balance")
class BalanceController(
    private val balanceFacade: BalanceFacade
) {
    @PostMapping("/recharge")
    fun rechargeBalance(
        request: HttpServletRequest,
        @RequestBody balanceRequest: BalanceRequest
    ): ResponseEntity<ApiResponse> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        balanceFacade.rechargeBalance(userToken, queueToken, balanceRequest)
        return ResponseEntity.ok(ApiResponse("Balance recharged successfully"))
    }

    @GetMapping("/inquiry")
    fun getBalance(request: HttpServletRequest): ResponseEntity<BalanceResponse> {
        val userToken = request.getAttribute("userToken") as String
        val queueToken = request.getAttribute("queueToken") as String
        val response = balanceFacade.getBalance(userToken, queueToken)
        return ResponseEntity.ok(response)
    }
}
