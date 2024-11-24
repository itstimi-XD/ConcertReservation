package io.hhplus.concertreservationservice.domain.beauty

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class BeautyConsumerService {
    @KafkaListener(topics = ["pretty"], groupId = "test-pretty")
    fun consumeBeauty(message: String) {
        println("Received message: $message")
    }
}