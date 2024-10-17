package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import io.hhplus.concertreservationservice.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: Long,

    val concertScheduleId: Long,

    val seatId: Long,

    @Column(nullable = false)
    var status: String, // "pending", "reserved", "expired"

    @Column(name = "expiration_time", nullable = false, columnDefinition = "TIMESTAMP")
    val expirationTime: LocalDateTime,

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now()

)
