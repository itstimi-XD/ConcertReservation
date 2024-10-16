package io.hhplus.concertreservationservice.domain.queue

import io.hhplus.concertreservationservice.domain.common.Auditable
import jakarta.persistence.*

@Entity
@EntityListeners(Auditable::class)
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
    var status: String // "waiting", "pass"

) : Auditable()

