package io.hhplus.concertreservationservice.exception

import org.springframework.boot.logging.LogLevel

enum class ErrorType(
    val errorCode: ErrorCode,
    val message: String,
    val logLevel: LogLevel
) {
    INTERNAL_ERROR(ErrorCode.BUSINESS_ERROR, "내부 서버 에러가 발생했습니다.", LogLevel.ERROR),
    RESOURCE_NOT_FOUND(ErrorCode.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.", LogLevel.WARN),
    INSUFFICIENT_BALANCE(ErrorCode.CLIENT_ERROR, "잔액이 부족합니다.", LogLevel.WARN),
}
