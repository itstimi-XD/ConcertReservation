package io.hhplus.concertreservationservice.domain.outbox

import jakarta.persistence.*
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "outbox_event")
class OutboxEvent(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    val eventType: String,

    @Lob
    val payload: String,

    @Enumerated(EnumType.STRING)
    var status: OutboxStatus = OutboxStatus.PENDING,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime = LocalDateTime.now()
)
