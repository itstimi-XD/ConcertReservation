package io.hhplus.concertreservationservice.infrastructure.concert

import io.hhplus.concertreservationservice.domain.concert.Concert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JpaConcertRepository: JpaRepository<Concert, Long> {
    override fun findById(id: Long): Optional<Concert>
}