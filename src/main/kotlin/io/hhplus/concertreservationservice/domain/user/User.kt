package io.hhplus.concertreservationservice.domain.user

import io.hhplus.concertreservationservice.domain.common.Auditable
import jakarta.persistence.*

@Entity
@EntityListeners(Auditable::class)
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var balance: Double = 0.0
): Auditable()
