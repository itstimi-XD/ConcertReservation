package io.hhplus.concertreservationservice.domain.reservation

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "concert_schedule_id", nullable = false)
    val concertScheduleId: Long,

    @Column(name = "seat_id", nullable = false)
    val seatId: Long,

    @Column(name = "seat_number", nullable = false)
    val seatNumber: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReservationStatus, // "reserved", "payment_completed", "cancelled"

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "expiration_time", nullable = false, columnDefinition = "TIMESTAMP")
    val expirationTime: LocalDateTime
)
