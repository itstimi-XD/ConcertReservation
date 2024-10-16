package io.hhplus.concertreservationservice.infrastructure.queue

import io.hhplus.concertreservationservice.domain.queue.QueueEntry
import io.hhplus.concertreservationservice.domain.queue.QueueRepository
import org.springframework.stereotype.Repository

@Repository
class QueueRepositoryInMemoryImpl : QueueRepository {

    private val queueEntries = mutableListOf<QueueEntry>()

    override fun save(queueEntry: QueueEntry): QueueEntry {
        queueEntries.add(queueEntry)
        return queueEntry
    }

    override fun findByToken(token: String): QueueEntry? {
        return queueEntries.find { it.token == token }
    }

    override fun count(): Long {
        return queueEntries.size.toLong()
    }

    override fun deleteByToken(token: String) {
        queueEntries.removeIf { it.token == token }
    }
}
