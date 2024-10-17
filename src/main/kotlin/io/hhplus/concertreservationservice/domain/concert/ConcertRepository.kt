package io.hhplus.concertreservationservice.domain.concert

interface ConcertRepository {
    fun findById(id: Long): Concert?
}