package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import jakarta.persistence.*


@Entity
@Table(name = "seats")
data class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_schedule_id", nullable = false)
    val concertSchedule: ConcertSchedule,

    @Column(name = "seat_number", nullable = false)
    val seatNumber: Int,

    @Column(nullable = false)
    var status: String, // "available", "pending", "reserved"

    @Column(name = "user_id")
    var userId: Long? = null
)
