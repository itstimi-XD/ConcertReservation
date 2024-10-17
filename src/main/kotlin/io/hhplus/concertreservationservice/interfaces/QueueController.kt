package io.hhplus.concertreservationservice.interfaces

import io.hhplus.concertreservationservice.application.queue.QueueFacade
import io.hhplus.concertreservationservice.interfaces.dto.QueueTokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/queue")
class QueueController(
    private val queueFacade: QueueFacade
) {

    @PostMapping("/register")
    fun registerInQueue(@RequestHeader("Authorization") userToken: String): ResponseEntity<QueueTokenResponse> {
        val response = queueFacade.registerInQueue(userToken)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/status")
    fun getQueueStatus(@RequestParam queueToken: String): ResponseEntity<QueueTokenResponse> {
        val response = queueFacade.getQueueStatus(queueToken)
        return ResponseEntity.ok(response)
    }

}