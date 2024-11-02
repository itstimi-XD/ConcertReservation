package io.hhplus.concertreservationservice.domain.queue

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "queue_entries")
class QueueEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "queue_token", nullable = false)
    val queueToken: String,

    @Column(name = "concert_schedule_id", nullable = false)
    val concertScheduleId: Long,

    @Column(name = "queue_position", nullable = false)
    var queuePosition: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: QueueStatus,

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now()
){
    fun complete() {
        this.status = QueueStatus.COMPLETED
        this.updatedAt = LocalDateTime.now()
    }

    fun isCompleted(): Boolean {
        return this.status == QueueStatus.COMPLETED
    }

    fun isExpired(expirationMinutes: Long): Boolean {
        return this.updatedAt.isBefore(LocalDateTime.now().minusMinutes(expirationMinutes))
    }

    fun isWaiting(): Boolean {
        return this.status == QueueStatus.WAITING
    }

    fun updatePosition(newPosition: Int) {
        this.queuePosition = newPosition
        this.updatedAt = LocalDateTime.now()
    }
}

