package io.hhplus.concertreservationservice.application.scheduler

import com.fasterxml.jackson.databind.ObjectMapper
import io.hhplus.concertreservationservice.domain.outbox.OutboxStatus
import io.hhplus.concertreservationservice.infrastructure.kafka.KafkaProducer
import io.hhplus.concertreservationservice.infrastructure.outbox.OutboxEventRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OutboxEventScheduler(
    private val outboxEventRepository: OutboxEventRepository,
    private val kafkaProducer: KafkaProducer,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(OutboxEventScheduler::class.java)

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    @Transactional
    fun processOutboxEvents() {
        val pendingEvents = outboxEventRepository.findByStatus(OutboxStatus.PENDING)

        pendingEvents.forEach { event ->
            try {
                // 카프카로 메시지 발행
                kafkaProducer.send("payment-events", event.id.toString(), event.payload)

                // 상태 업데이트
                event.status = OutboxStatus.SENT
                outboxEventRepository.save(event)
            } catch (ex: Exception) {
                logger.error("Failed to send event to Kafka: ${event.id}", ex)

                // 실패한 경우 상태 업데이트
                event.status = OutboxStatus.FAILED
                outboxEventRepository.save(event)
            }
        }
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    fun retryFailedEvents() {
        val failedEvents = outboxEventRepository.findByStatus(OutboxStatus.FAILED)

        failedEvents.forEach { event ->
            try {
                // 카프카로 메시지 재발행
                kafkaProducer.send("payment-events", event.id.toString(), event.payload)

                // 상태 업데이트
                event.status = OutboxStatus.SENT
                outboxEventRepository.save(event)
            } catch (ex: Exception) {
                logger.error("Failed to resend event to Kafka: ${event.id}", ex)
                // 재시도 횟수 제한 등을 고려할 수 있다
            }
        }
    }
}