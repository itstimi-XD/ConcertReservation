package io.hhplus.concertreservationservice.domain.user

import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "concert_reservation")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    var balance: Double = 0.0
)
