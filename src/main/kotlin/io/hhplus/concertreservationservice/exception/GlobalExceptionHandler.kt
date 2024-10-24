package io.hhplus.concertreservationservice.exception

import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse> {
        // 로그 레벨에 따른 로그 출력
        when (e.errorType.logLevel) {
            LogLevel.ERROR -> log.error(e.message, e)
            LogLevel.WARN -> log.warn(e.message, e)
            else -> log.info(e.message, e)
        }

        // HTTP 상태 코드 매핑
        val status = when (e.errorType.errorCode) {
            ErrorCode.NOT_FOUND -> HttpStatus.NOT_FOUND
            ErrorCode.CLIENT_ERROR -> HttpStatus.BAD_REQUEST
            ErrorCode.DATABASE_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
            ErrorCode.AUTHENTICATION_ERROR -> HttpStatus.UNAUTHORIZED
            ErrorCode.SEAT_ERROR -> HttpStatus.CONFLICT
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        // 에러 응답 생성
        val errorResponse = ErrorResponse(
            status = "FAIL",
            errorCode = e.errorType.errorCode.name,
            errorMessage = e.errorType.message,
            data = e.payload
        )

        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(ConcurrentModificationException::class)
    fun handleConcurrentModificationException(e: ConcurrentModificationException): ResponseEntity<ErrorResponse> {
        log.warn(e.message, e)

        val errorResponse = ErrorResponse(
            status = "FAIL",
            errorCode = "CONCURRENCY_ERROR",
            errorMessage = e.message ?: "동시 작업으로 인해 요청을 처리할 수 없습니다. 다시 시도해 주세요."
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    // 기타 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unhandled exception", e)
        val errorResponse = ErrorResponse(
            status = "FAIL",
            errorCode = "INTERNAL_SERVER_ERROR",
            errorMessage = "예상하지 못한 순간 등장하지 불쑥"
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
