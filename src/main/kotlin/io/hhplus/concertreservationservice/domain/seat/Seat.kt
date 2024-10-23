package io.hhplus.concertreservationservice.domain.seat

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "seats")
data class Seat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "concert_schedule_id", nullable = false)
    val concertScheduleId: Long,

    @Column(name = "seat_number", nullable = false)
    val seatNumber: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_status", nullable = false)
    var seatStatus: SeatStatus, // "occupied", "available"

    @Column(name = "user_id")
    var userId: Long? = null,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version // 낙관적 락을 위한 버전 필드
    @Column(name = "version")
    var version: Long = 0
)

