package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.domain.common.Auditable
import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import io.hhplus.concertreservationservice.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@EntityListeners(Auditable::class)
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    val concertSchedule: ConcertSchedule,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    val seat: Seat,

    @Column(nullable = false)
    var status: String, // "pending", "reserved", "expired"

    @Column(name = "expiration_time", nullable = false)
    val expirationTime: LocalDateTime

) : Auditable()
