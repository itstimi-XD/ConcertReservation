package io.hhplus.concertreservationservice.application.queue

import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.user.UserService
import io.hhplus.concertreservationservice.interfaces.dto.QueueRegistrationRequest
import io.hhplus.concertreservationservice.interfaces.dto.QueueTokenResponse
import org.springframework.stereotype.Service

@Service
class QueueFacade(
    private val userService: UserService,
    private val queueService: QueueService
) {

    fun registerInQueue(userToken: String, request: QueueRegistrationRequest): QueueTokenResponse {
        val userId = userService.getUserIdFromToken(userToken)
        val concertScheduleId = request.concertScheduleId
        val queueEntry = queueService.registerUserInQueue(userId, concertScheduleId)
        val estimatedWaitTime = queueService.calculateEstimatedWaitTime(queueEntry.queuePosition)
        return QueueTokenResponse(
            queueToken = queueEntry.queueToken,
            queuePosition = queueEntry.queuePosition,
            estimatedWaitTime = estimatedWaitTime)
    }

    fun getQueueStatus(queueToken: String): QueueTokenResponse {
        val queueEntry = queueService.getQueueStatus(queueToken) ?: throw IllegalArgumentException("Invalid queue token")
        val estimatedWaitTime = queueService.calculateEstimatedWaitTime(queueEntry.queuePosition)
        return QueueTokenResponse(
            queueToken = queueEntry.queueToken,
            queuePosition = queueEntry.queuePosition,
            estimatedWaitTime = estimatedWaitTime)
    }
}