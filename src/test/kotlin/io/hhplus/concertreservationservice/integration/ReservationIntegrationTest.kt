package io.hhplus.concertreservationservice.integration

import io.hhplus.concertreservationservice.application.concert.ConcertFacade
import io.hhplus.concertreservationservice.domain.concert.Concert
import io.hhplus.concertreservationservice.domain.concert.ConcertSchedule
import io.hhplus.concertreservationservice.domain.concert.ConcertScheduleRepository
import io.hhplus.concertreservationservice.domain.concert.ConcertRepository
import io.hhplus.concertreservationservice.domain.seat.Seat
import io.hhplus.concertreservationservice.domain.seat.SeatRepository
import io.hhplus.concertreservationservice.domain.seat.SeatStatus
import io.hhplus.concertreservationservice.domain.user.User
import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.ReservationRequest
import io.hhplus.concertreservationservice.interfaces.dto.ReservationResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest
class ReservationIntegrationTest {

    @Autowired
    private lateinit var concertFacade: ConcertFacade

    @Autowired
    private lateinit var concertRepository: ConcertRepository

    @Autowired
    private lateinit var concertScheduleRepository: ConcertScheduleRepository

    @Autowired
    private lateinit var seatRepository: SeatRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var authUtil: AuthUtil

    private lateinit var userToken: String
    private lateinit var queueToken: String
    private lateinit var user: User
    private lateinit var concertSchedule: ConcertSchedule

    @BeforeEach
    fun setUp() {
        // 사용자 생성
        user = User(name = "testUser", email = "s", password = "testPassword")
        user = userRepository.save(user)

        // 사용자 토큰 생성
        userToken = "user-${user.id}"
        queueToken = "completed"

        // 대기열 통과 상태로 설정
        // 실제로는 QueueService를 통해 처리해야 하지만, 여기서는 간단히 설정
        // authUtil 또는 관련 서비스를 Mocking하여 대기열 통과 상태로 만듭니다.

        // 콘서트 및 스케줄 생성
        val concert = Concert(title = "Test Concert", description = "Test Description")
        concertRepository.save(concert)

        concertSchedule = ConcertSchedule(
            concertId = concert.id,
            concertDate = LocalDateTime.now().plusDays(1),
            availableDate = LocalDateTime.now(),
            totalSeats = 100,
            remainingSeats = 100
        )
        concertScheduleRepository.save(concertSchedule)

        // 좌석 생성
        val seats = (1..100).map { seatNumber ->
            Seat(
                concertScheduleId = concertSchedule.id,
                seatNumber = seatNumber,
                seatStatus = SeatStatus.AVAILABLE

            )
        }
        seatRepository.saveAll(seats)
    }

    @Test
    fun `좌석 예약 통합 테스트`() {
        // Given
        val reservationRequest = ReservationRequest(
            concertScheduleId = concertSchedule.id,
            seatNumber = 1
        )

        // When
        val reservationResponse: ReservationResponse = concertFacade.reserveSeat(userToken, queueToken, reservationRequest)

        // Then
        assertNotNull(reservationResponse)
        assertEquals("reserved", reservationResponse.status)
        assertEquals(1, reservationResponse.seatNumber)
        assertEquals(concertSchedule.id, reservationResponse.concertScheduleId)

        // 좌석 상태 확인
        val seat = seatRepository.findByConcertScheduleIdAndSeatNumberWithLock(concertSchedule.id, 1)
        assertNotNull(seat)
        assertEquals("occupied", seat?.seatStatus)
        assertEquals(user.id, seat?.userId)
    }
}
