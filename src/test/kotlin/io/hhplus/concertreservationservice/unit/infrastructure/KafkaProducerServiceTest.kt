package io.hhplus.concertreservationservice.unit.infrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.core.KafkaTemplate
import kotlin.test.Test

@SpringBootTest
class KafkaProducerServiceTest {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Test
    fun `알흠다움 발행 테스트`() {
        // Given
        val topic = "pretty"
        val message = "Hello, Pretty!"

        // When
        val future = kafkaTemplate.send(topic, message)

        // Then
        val result = future.get()
        assertEquals(topic, result.recordMetadata.topic())
        assertEquals(message, result.producerRecord.value())
        println("메세지가 아주 성공적으로다가 보내졌어요! : ${result.producerRecord}")

    }
}
