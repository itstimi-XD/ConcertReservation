package io.hhplus.concertreservationservice.domain.balance

import io.hhplus.concertreservationservice.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "balance_history")
data class BalanceHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val amount: Double,

    @Column(name = "transaction_date", nullable = false)
    val transactionDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "transaction_type", nullable = false)
    val transactionType: String // "charge", "payment"
)
