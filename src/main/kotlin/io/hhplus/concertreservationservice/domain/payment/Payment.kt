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

    val userId: Long,

    val reservationId: Long,

    @Column(nullable = false)
    val amount: Double,

    @Column(name = "payment_date", nullable = false, columnDefinition = "TIMESTAMP")
    val paymentDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var status: String // "completed", "failed"
)
