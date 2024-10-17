package io.hhplus.concertreservationservice.domain.concert

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "concert_schedules")
data class ConcertSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "concert_id", nullable = false)
    val concertId: Long,

    @Column(name = "concert_date", nullable = false, columnDefinition = "DATETIME")
    val concertDate: LocalDateTime,

    @Column(name = "available_date", nullable = false, columnDefinition = "DATETIME")
    val availableDate: LocalDateTime,

    @Column(name = "total_seats", nullable = false)
    val totalSeats: Int = 50,

    @Column(name = "remaining_seats", nullable = false)
    var remainingSeats: Int = 50,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
