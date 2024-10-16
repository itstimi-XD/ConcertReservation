package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//@Repository
//interface QueueRepositoryJpaImpl : QueueRepository, JpaRepository<QueueEntry, String> {
//
//    override fun save(queueEntry: QueueEntry): QueueEntry
//
//    override fun findByToken(token: String): QueueEntry?
//
//    override fun deleteByToken(token: String)
//
//    override fun count(): Long
//}
