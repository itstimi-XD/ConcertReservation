package io.hhplus.concertreservationservice.domain.queue

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "queue_entries")
data class QueueEntry(
    @Id
    val token: String,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val queuePosition: Long,

    @Column(nullable = false)
    val issuedAt: LocalDateTime,

    @Column(nullable = false)
    val estimatedWaitTime: Long // 초 단위
)
