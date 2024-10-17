//package io.hhplus.concertreservationservice.domain.common
//
//import jakarta.persistence.Column
//import jakarta.persistence.PrePersist
//import jakarta.persistence.PreUpdate
//import java.time.LocalDateTime
//
//open class Auditable {
//
//    @Column(name = "created_at", nullable = false, updatable = false)
//    var createdAt: LocalDateTime = LocalDateTime.now()
//
//    @Column(name = "updated_at", nullable = false)
//    var updatedAt: LocalDateTime = LocalDateTime.now()
//
//    @PrePersist
//    fun onPrePersist() {
//        createdAt = LocalDateTime.now()
//        updatedAt = LocalDateTime.now()
//    }
//
//    @PreUpdate
//    fun onPreUpdate() {
//        updatedAt = LocalDateTime.now()
//    }
//}
