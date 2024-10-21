package io.hhplus.concertreservationservice.interfaces.api.user

import io.hhplus.concertreservationservice.application.auth.AuthFacade
import io.hhplus.concertreservationservice.interfaces.dto.AuthResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import io.hhplus.concertreservationservice.interfaces.dto.LoginRequest
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authFacade: AuthFacade
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        val token = authFacade.login(loginRequest.username, loginRequest.password)
        return ResponseEntity.ok(token)
    }
}