package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.Concert
import io.hhplus.concertreservationservice.domain.concert.ConcertRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConcertRepositoryImpl(
    private val jpaConcertRepository: JpaConcertRepository
): ConcertRepository {
    override fun findById(id: Long): Concert? {
        return jpaConcertRepository.findById(id).orElse(null)
    }
}