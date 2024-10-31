package io.hhplus.concertreservationservice.domain.reservation

import io.hhplus.concertreservationservice.exception.BusinessException
import io.hhplus.concertreservationservice.exception.ErrorType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reservations")
data class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "concert_schedule_id", nullable = false)
    val concertScheduleId: Long,

    @Column(name = "seat_id", nullable = false)
    val seatId: Long,

    @Column(name = "seat_number", nullable = false)
    val seatNumber: Int,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReservationStatus, // "reserved", "payment_completed", "cancelled"

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "expiration_time", nullable = false, columnDefinition = "TIMESTAMP")
    val expirationTime: LocalDateTime
){

    // 예약 만료 여부 확인
    fun isExpired(currentTime: LocalDateTime): Boolean {
        return expirationTime.isBefore(currentTime)
    }

    // 상태 업데이트 메서드
    fun updateStatus(newStatus: ReservationStatus, currentTime: LocalDateTime) {
        status = newStatus
        updatedAt = currentTime
    }

    // 예약 확인 메서드
    fun confirmReservation(currentTime: LocalDateTime = LocalDateTime.now()) {
        if (isExpired(currentTime)) {
            throw BusinessException(ErrorType.INVALID_RESERVATION_STATUS, "Reservation has expired")
        }
        updateStatus(ReservationStatus.PAYMENT_COMPLETED, currentTime)
    }

    // 예약 취소 메서드
    fun cancelReservation(currentTime: LocalDateTime = LocalDateTime.now()) {
        updateStatus(ReservationStatus.CANCELLED, currentTime)
    }
}
