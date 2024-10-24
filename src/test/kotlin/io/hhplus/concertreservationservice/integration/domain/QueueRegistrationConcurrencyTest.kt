package io.hhplus.concertreservationservice.integration.domain

import io.hhplus.concertreservationservice.domain.queue.QueueService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class QueueRegistrationConcurrencyTest {

    @Autowired
    private lateinit var queueService: QueueService

    @Test
    fun `동시에 여러 사용자가 대기열에 등록하면 모든 사용자가 정상적으로 등록되어야 한다`() {
        val numberOfThreads = 10
        val latch = CountDownLatch(numberOfThreads)
        val executorService = Executors.newFixedThreadPool(numberOfThreads)

        val userIds = (1..numberOfThreads).map { it.toLong() }
        val concertScheduleId = 1L

        val queuePositions = mutableListOf<Int>()

        userIds.forEach { userId ->
            executorService.execute {
                try {
                    val queueEntry = queueService.registerUserInQueue(userId, concertScheduleId)
                    synchronized(queuePositions) {
                        queuePositions.add(queueEntry.queuePosition)
                    }
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await()

        assertEquals(numberOfThreads, queuePositions.size, "대기열 등록된 사용자 수는 $numberOfThreads 이어야 합니다.")
        val expectedPositions = (1..numberOfThreads).toList()
        assertTrue(queuePositions.containsAll(expectedPositions), "대기열 순번이 올바르지 않습니다.")
    }
}
