package io.hhplus.concertreservationservice.unit.application

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.queue.QueueStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class QueueServiceTest {

    private lateinit var queueRepository: QueueRepository
    private lateinit var queueService: QueueService
    private val passLimit = 10
    private val expirationMinutes = 60L

    @BeforeEach
    fun setUp() {
        queueRepository = Mockito.mock(QueueRepository::class.java)
        queueService = QueueService(queueRepository, passLimit, expirationMinutes)
    }

    @Test
    fun `사용자가 대기열에 진입하면 대기열 토큰과 대기 순번이 발급된다`() {
        // Given
        val userId = 1L
        whenever(queueRepository.count()).thenReturn(0)
        whenever(queueRepository.save(any()))
            .thenAnswer { invocation ->
                invocation.arguments[0]
            }

        // When
        val queueEntry = queueService.registerUserInQueue(userId, 1L)

        // Then
        assertNotNull(queueEntry.queueToken)
        assertEquals(1, queueEntry.queuePosition)
        assertEquals(userId, queueEntry.userId)
    }

    @Test
    fun `유효한 토큰으로 대기열 상태를 조회할 수 있다`() {
        // Given
        val token = "valid-token"
        val queueEntry = QueueEntry(
            id = 1L,
            userId = 1L,
            queueToken = token,
            concertScheduleId = 1L,
            queuePosition = 1,
            status = QueueStatus.WAITING,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        Mockito.`when`(queueRepository.findByQueueToken(token)).thenReturn(queueEntry)

        // When
        val result = queueService.getQueueStatus(token)

        // Then
        assertNotNull(result)
        assertEquals(token, result?.queueToken)
    }

    @Test
    fun `유효하지 않은 토큰으로 대기열 상태를 조회하면 null을 반환한다`() {
        // Given
        val invalidToken = "invalid-token"
        Mockito.`when`(queueRepository.findByQueueToken(invalidToken)).thenReturn(null)

        // When
        val result = queueService.getQueueStatus(invalidToken)

        // Then
        assertNull(result)
    }
}