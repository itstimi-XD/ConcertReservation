package io.hhplus.concertreservationservice.infrastructure.kafka

interface KafkaProducer {
    fun send(topic: String, key: String, message: String)
}