package io.hhplus.concertreservationservice.interfaces.common

import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AuthUtil {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var queueService: QueueService

    fun getUserIdIfQueueTokenValid(userToken: String, queueToken: String): Long {
        // 대기열 토큰 검증
        if(!queueService.isValidQueueToken(queueToken)) {
            throw IllegalArgumentException("Invalid queue token")
        }

        // 사용자 토큰 에서 userId 추출
        return userService.getUserIdFromToken(userToken)
    }

}