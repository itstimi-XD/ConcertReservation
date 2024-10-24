package io.hhplus.concertreservationservice.integration.domain

import io.hhplus.concertreservationservice.domain.reservation.Reservation
import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime

class ReservationTest {

    @Test
    fun `만료된 예약은 isExpired가 true를 반환한다`() {
        // given
        val reservation = Reservation(
            userId = 1L,
            concertScheduleId = 1L,
            seatId = 1L,
            seatNumber = 1,
            status = ReservationStatus.RESERVED,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            expirationTime = LocalDateTime.now().minusMinutes(1) // 이미 만료된 시간
        )

        // when
        val isExpired = reservation.isExpired(LocalDateTime.now())

        // then
        assertTrue(isExpired)
    }

    @Test
    fun `updateStatus를 호출하면 예약 상태와 업데이트 시간이 변경된다`() {
        // given
        val reservation = Reservation(
            userId = 1L,
            concertScheduleId = 1L,
            seatId = 1L,
            seatNumber = 1,
            status = ReservationStatus.RESERVED,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            expirationTime = LocalDateTime.now().plusMinutes(5)
        )
        val newStatus = ReservationStatus.PAYMENT_COMPLETED
        val currentTime = LocalDateTime.now()

        // when
        reservation.updateStatus(newStatus, currentTime)

        // then
        assertEquals(newStatus, reservation.status)
        assertEquals(currentTime, reservation.updatedAt)
    }
}