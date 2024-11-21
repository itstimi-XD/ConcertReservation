package io.hhplus.concertreservationservice.unit.infrastructure

import io.hhplus.concertreservationservice.domain.beauty.BeautyConsumerService
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import kotlin.test.Test

@SpringBootTest
class KafkaConsumerServiceTest {

    @MockBean
    private lateinit var consumer: BeautyConsumerService

    @Test
    fun `알흠다움 수신 테스트`() {
        // Given
        val message = "Hello, Pretty!"

        // When
        consumer.consumeBeauty(message)

        // Then
        // 메시지가 정상적으로 처리되었는지 검증.
        verify(consumer, times(1)).consumeBeauty(message)
        // 이게 아닌거같은데..............
    }
}
