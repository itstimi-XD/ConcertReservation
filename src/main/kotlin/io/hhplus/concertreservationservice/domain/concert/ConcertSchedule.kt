package io.hhplus.concertreservationservice.domain.concert

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "concert_schedules")
data class ConcertSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    val concert: Concert,

    @Column(name = "concert_date", nullable = false)
    val concertDate: LocalDate,

    @Column(name = "total_seats", nullable = false)
    val totalSeats: Int = 50
)
