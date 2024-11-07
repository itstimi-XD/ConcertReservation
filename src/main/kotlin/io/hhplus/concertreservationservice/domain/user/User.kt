package io.hhplus.concertreservationservice.domain.user

import io.hhplus.concertreservationservice.exception.BusinessException
import io.hhplus.concertreservationservice.exception.ErrorType
import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "concert_reservation")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    var balance: Double = 0.0
){
    // 잔액 차감 메서드
    fun deductBalance(amount: Double) {
        if (this.balance < amount) {
            throw  BusinessException(ErrorType.INSUFFICIENT_BALANCE)
        }
        this.balance -= amount
    }

    // 잔액 충전 메서드
    fun addBalance(amount: Double) {
        this.balance += amount
    }

}
