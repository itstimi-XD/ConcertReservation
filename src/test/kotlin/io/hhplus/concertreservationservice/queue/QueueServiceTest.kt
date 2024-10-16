package io.hhplus.concertreservationservice.queue

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import java.time.LocalDateTime

class QueueServiceTest {

    private lateinit var queueRepository: QueueRepository
    private lateinit var queueService: QueueService

    @BeforeEach
    fun setUp() {
        queueRepository = Mockito.mock(QueueRepository::class.java)
        queueService = QueueService(queueRepository)
    }

    @Test
    fun `사용자가 대기열에 진입하면 대기열 토큰과 대기 순번이 발급된다`() {
        // Given
        val userId = 1L
        Mockito.`when`(queueRepository.count()).thenReturn(0)
        Mockito.`when`(queueRepository.save(Mockito.any(QueueEntry::class.java)))
            .thenAnswer { invocation ->
                invocation.arguments[0] as QueryEntry
            }

        // When
        val queueEntry = queueService.issueToken(userId)

        // Then
        assertNotNull(queueEntry.token)
        assertEquals(1, queueEntry.queuePosition)
        assertEquals(userId, queueEntry.userId)
    }

    @Test
    fun `유효한 토큰으로 대기열 상태를 조회할 수 있다`() {
        // Given
        val token = "valid-token"
        val queueEntry = QueueEntry(
            token = token,
            userId = 1L,
            queuePosition = 1,
            issuedAt = LocalDateTime.now(),
            estimatedWaitTime = 60L
        )
        Mockito.`when`(queueRepository.findByToken(token)).thenReturn(queueEntry)

        // When
        val result = queueService.getQueueStatus(token)

        // Then
        assertNotNull(result)
        assertEquals(token, result.token)
    }

    @Test
    fun `유효하지 않은 토큰으로 대기열 상태를 조회하면 null을 반환한다`() {
        // Given
        val invalidToken = "invalid-token"
        Mockito.`when`(queueRepository.findByToken(invalidToken)).thenReturn(null)

        // When
        val result = queueService.getQueueStatus(invalidToken)

        // Then
        assertNull(result)
    }
}