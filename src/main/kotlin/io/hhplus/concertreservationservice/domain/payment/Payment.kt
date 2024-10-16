package io.hhplus.concertreservationservice.domain.payment

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    val reservation: Reservation,

    @Column(nullable = false)
    val amount: Double,

    @Column(name = "payment_date", nullable = false)
    val paymentDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var status: String // "completed", "failed"
)
