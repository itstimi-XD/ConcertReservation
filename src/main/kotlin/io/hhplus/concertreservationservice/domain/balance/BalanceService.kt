package io.hhplus.concertreservationservice.domain.balance

import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.exception.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BalanceService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun rechargeBalance(userId: Long, amount: Int) {
        val user = userRepository.findByIdForUpdate(userId)
            ?: throw ResourceNotFoundException("User not found")

        user.balance += amount
        userRepository.save(user)
    }

    fun getBalance(userId: Long): Int {
        val user = userRepository.findById(userId)
            ?: throw ResourceNotFoundException("User not found")
        return user.balance.toInt()
    }
}
