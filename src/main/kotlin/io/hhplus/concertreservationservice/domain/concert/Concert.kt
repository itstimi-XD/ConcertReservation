package io.hhplus.concertreservationservice.domain.concert

import jakarta.persistence.*

@Entity
@Table(name = "concerts")
data class Concert(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String
)
