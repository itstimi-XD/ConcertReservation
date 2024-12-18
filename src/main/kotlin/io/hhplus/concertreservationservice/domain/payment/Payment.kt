package io.hhplus.concertreservationservice.domain.payment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "reservation_id", nullable = false)
    val reservationId: Long,

    @Column(nullable = false)
    val amount: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: PaymentStatus, // "completed"

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now()
){
    companion object {
        fun create(userId: Long, reservationId: Long, amount: Int): Payment {
            return Payment(
                userId = userId,
                reservationId = reservationId,
                amount = amount,
                status = PaymentStatus.COMPLETED // 결제 완료 상태로 설정
            )
        }
    }
}

