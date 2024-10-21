package io.hhplus.concertreservationservice.application.balance

import io.hhplus.concertreservationservice.domain.balance.BalanceService
import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.BalanceRequest
import io.hhplus.concertreservationservice.interfaces.dto.BalanceResponse
import org.springframework.stereotype.Service

@Service
class BalanceFacade(
    private val balanceService: BalanceService,
    private val authUtil: AuthUtil
) {
    fun rechargeBalance(userToken: String, queueToken: String, request: BalanceRequest) {
        val userId = authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        balanceService.rechargeBalance(userId, request.amount)
    }

    fun getBalance(userToken: String, queueToken: String): BalanceResponse {
        val userId = authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)
        val balance = balanceService.getBalance(userId)
        return BalanceResponse(balance = balance)
    }
}
