package io.hhplus.concertreservationservice.infrastructure.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : KafkaProducer {
    override fun send(topic: String, key: String, message: String) {
        kafkaTemplate.send(topic, key, message)
    }
}