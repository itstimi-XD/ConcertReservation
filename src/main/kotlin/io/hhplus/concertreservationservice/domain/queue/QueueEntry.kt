package io.hhplus.concertreservationservice.domain.queue

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "queue_entries")
data class QueueEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "queue_position", nullable = false)
    val queuePosition: Int,

    @Column(nullable = false)
    var status: String, // "waiting", "pass"

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now()

)

