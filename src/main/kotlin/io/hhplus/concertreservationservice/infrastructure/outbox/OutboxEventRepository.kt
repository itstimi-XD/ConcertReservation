package io.hhplus.concertreservationservice.infrastructure.outbox

import io.hhplus.concertreservationservice.domain.outbox.OutboxEvent
import io.hhplus.concertreservationservice.domain.outbox.OutboxStatus
import org.springframework.data.jpa.repository.JpaRepository

// TODO: 도메인 계층에 인터페이스, 여기에는 Impl 클래스를 만들어서 구현하도록 변경
interface OutboxEventRepository : JpaRepository<OutboxEvent, Long> {
    fun findByStatus(status: OutboxStatus): List<OutboxEvent>
}