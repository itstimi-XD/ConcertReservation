package io.hhplus.concertreservationservice.application.auth

import io.hhplus.concertreservationservice.domain.user.UserRepository
import io.hhplus.concertreservationservice.interfaces.dto.AuthResponse
import org.springframework.stereotype.Service

@Service
class AuthFacade(
    private val userRepository: UserRepository,
//    private psswordEncoder: PasswordEncoder,
//    private val jwtTokenProvider: JwtTokenProvider
) {

    fun login(username: String, password: String): AuthResponse {

        val user = userRepository.findByName(username)
            ?: throw IllegalArgumentException("User not found")

        return AuthResponse(userToken = "user-${user.id}")
    }
}