package io.hhplus.concertreservationservice.domain.user

import org.springframework.stereotype.Service

@Service
class UserService {

    fun getUserIdFromToken(token: String): Long {
        // 간단한 토큰 파싱 로직
        // 실제로는 JWT 등을 사용하여 토큰에서 사용자 ID를 추출해야 합니다.
        // 여기서는 예시로 토큰이 "user-{userId}" 형식이라고 가정합니다.

        return if (token.startsWith("user-")) {
            token.removePrefix("user-").toLongOrNull() ?: throw IllegalArgumentException("Invalid token")
        } else {
            throw IllegalArgumentException("Invalid token")
        }
    }
}