package io.hhplus.concertreservationservice.integration.domain

import io.hhplus.concertreservationservice.domain.reservation.ReservationService
import io.hhplus.concertreservationservice.exception.SeatAlreadyOccupiedException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class SeatReservationConcurrencyTest {

    @Autowired
    private lateinit var reservationService: ReservationService

    @Test
    fun `동시에 같은 좌석을 예약하면 하나의 사용자만 성공해야 한다`() {
        val numberOfThreads = 10
        val latch = CountDownLatch(numberOfThreads)
        val executorService = Executors.newFixedThreadPool(numberOfThreads)

        val successes = mutableListOf<Long>()
        val failures = mutableListOf<Exception>()

        val userIds = (1..numberOfThreads).map { it.toLong() }
        val concertScheduleId = 1L
        val seatNumber = 1

        userIds.forEach { userId ->
            executorService.execute {
                try {
                    reservationService.reserveSeat(userId, concertScheduleId, seatNumber)
                    synchronized(successes) {
                        successes.add(userId)
                    }
                } catch (e: SeatAlreadyOccupiedException) {
                    synchronized(failures) {
                        failures.add(e)
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        assertEquals(1, successes.size, "성공한 예약은 한 건이어야 합니다.")
        assertEquals(numberOfThreads - 1, failures.size, "실패한 예약은 ${numberOfThreads - 1}건이어야 합니다.")
    }
}
