package io.hhplus.concertreservationservice.config.interceptor

import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.user.UserService
import io.hhplus.concertreservationservice.exception.BusinessException
import io.hhplus.concertreservationservice.exception.InvalidTokenException
import io.hhplus.concertreservationservice.exception.QueueException
import io.hhplus.concertreservationservice.exception.ErrorType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    private val userService: UserService,
    private val queueService: QueueService
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val userToken = request.getHeader("User-Token")
        val queueToken = request.getHeader("Queue-Token")

        try {
            // 사용자 토큰 검증
            val userId = userService.getUserIdFromToken(userToken ?: throw InvalidTokenException("User token is missing"))
            request.setAttribute("userToken", userToken)
            request.setAttribute("userId", userId)

            // 대기열 토큰이 필요한 경우 검증
            if (requiresQueueToken(request.requestURI)) {
                if (queueToken == null || !queueService.isValidQueueToken(queueToken)) {
                    throw QueueException(ErrorType.INVALID_QUEUE_TOKEN, "queueToken: $queueToken")
                }
                // TODO : 컨트롤러에서도 getAttribute로 필요한 것 받아서 사용하도록 수정 필요
                request.setAttribute("queueToken", queueToken)
            }
        } catch (e: BusinessException) {
            // 예외 발생 시 에러 응답 전송
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.errorType.message)
            return false
        }

        // 유효한 토큰인 경우 요청을 처리하도록 허용
        return true
    }

    private fun requiresQueueToken(requestURI: String): Boolean {
        // TODO : 대기열 토큰이 필요한 경로를 지정
//        val pathsRequiringQueueToken = listOf("/api/reservations", "/api/seat")
        val pathsRequiringQueueToken = emptyList<String>();
        return pathsRequiringQueueToken.any { requestURI.startsWith(it) }
    }
}
