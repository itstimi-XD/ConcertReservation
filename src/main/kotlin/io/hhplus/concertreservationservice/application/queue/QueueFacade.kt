package io.hhplus.concertreservationservice.application.queue

import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.queue.QueueTokenService
import io.hhplus.concertreservationservice.domain.user.UserService
import io.hhplus.concertreservationservice.exception.ErrorType
import io.hhplus.concertreservationservice.exception.QueueException
import io.hhplus.concertreservationservice.interfaces.dto.QueueRegistrationRequest
import io.hhplus.concertreservationservice.interfaces.dto.QueueTokenResponse
import org.springframework.stereotype.Service

@Service
class QueueFacade(
    private val userService: UserService,
    private val queueService: QueueService,
    private val queueTokenService: QueueTokenService
) {

    fun registerInQueue(userToken: String, request: QueueRegistrationRequest): QueueTokenResponse {
        val userId = userService.getUserIdFromToken(userToken)
        val concertScheduleId = request.concertScheduleId

        // 대기열 등록
        val queueEntry = queueService.registerUserInQueue(userId, concertScheduleId)
        val estimatedWaitTime = queueService.calculateEstimatedWaitTime(queueEntry.queuePosition)

        // 토큰을 Redis에 저장
        queueTokenService.saveQueueToken(queueEntry.queueToken, userId)


        return QueueTokenResponse(
            queueToken = queueEntry.queueToken,
            queuePosition = queueEntry.queuePosition,
            estimatedWaitTime = estimatedWaitTime
        )
    }

    fun getQueueStatus(queueToken: String): QueueTokenResponse {
        val userId = queueTokenService.getUserIdByQueueToken(queueToken)
            ?: throw QueueException(ErrorType.INVALID_QUEUE_TOKEN, "queueToken: $queueToken")

        val queueEntry = queueService.getQueueStatus(queueToken)
            ?: throw QueueException(ErrorType.INVALID_QUEUE_TOKEN, "queueToken: $queueToken")

        val estimatedWaitTime = queueService.calculateEstimatedWaitTime(queueEntry.queuePosition)
        return QueueTokenResponse(
            queueToken = queueEntry.queueToken,
            queuePosition = queueEntry.queuePosition,
            estimatedWaitTime = estimatedWaitTime
        )
    }
}
