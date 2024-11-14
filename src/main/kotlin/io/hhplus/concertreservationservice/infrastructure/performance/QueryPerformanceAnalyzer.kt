package io.hhplus.concertreservationservice.infrastructure.performance

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Component

@Component
class QueryPerformanceAnalyzer(
    @PersistenceContext private val entityManager: EntityManager
) {
    fun explainQuery(query: String): MutableList<Any?> {
        val nativeQuery = entityManager.createNativeQuery("EXPLAIN $query")
        return nativeQuery.resultList as MutableList<Any?>
    }
}
