package io.hhplus.concertreservationservice.domain.user

import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
//    private psswordEncoder: PasswordEncoder,
//    private val jwtTokenProvider: JwtTokenProvider
) {

    fun login(username: String, password: String): String {

        val user = userRepository.findByName(username)
            ?: throw IllegalArgumentException("User not found")

        return "user-${user.id}"
    }
}