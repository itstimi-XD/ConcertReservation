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
    INVALID_QUEUE_TOKEN(ErrorCode.INVALID_REQUEST, "유효하지 않은 큐 토큰입니다.", LogLevel.WARN),
    USER_ALREADY_IN_QUEUE(ErrorCode.INVALID_REQUEST, "사용자가 이미 대기열에 등록되어 있습니다.", LogLevel.WARN),
    QUEUE_EMPTY(ErrorCode.INVALID_REQUEST, "대기열이 비어있습니다.", LogLevel.WARN),
    INVALID_SEAT_STATUS(ErrorCode.INVALID_REQUEST, "유효하지 않은 좌석 상태입니다.", LogLevel.WARN),
    SEAT_ALREADY_OCCUPIED(ErrorCode.SEAT_ERROR, "좌석이 이미 점유되었습니다.", LogLevel.WARN),
    CONCURRENCY_ERROR(ErrorCode.CONCURRENCY_ERROR, "동시 작업으로 인해 요청을 처리할 수 없습니다. 다시 시도해 주세요.", LogLevel.WARN),
    INVALID_TOKEN(ErrorCode.AUTHENTICATION_ERROR, "유효하지 않은 토큰입니다.", LogLevel.WARN),
    SEAT_ALREADY_RESERVED(ErrorCode.SEAT_ERROR, "좌석이 이미 예약되었습니다.", LogLevel.WARN),
    INVALID_RESERVATION_STATUS(ErrorCode.INVALID_REQUEST, "유효하지 않은 예약 상태입니다.", LogLevel.WARN),

}
