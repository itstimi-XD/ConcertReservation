package io.hhplus.concertreservationservice.application.payment

import io.hhplus.concertreservationservice.domain.payment.PaymentService
import io.hhplus.concertreservationservice.domain.payment.PaymentStatus
import io.hhplus.concertreservationservice.domain.queue.QueueService
import io.hhplus.concertreservationservice.domain.reservation.ReservationService
import io.hhplus.concertreservationservice.domain.reservation.ReservationStatus
import io.hhplus.concertreservationservice.domain.seat.SeatService
import io.hhplus.concertreservationservice.interfaces.common.AuthUtil
import io.hhplus.concertreservationservice.interfaces.dto.PaymentRequest
import io.hhplus.concertreservationservice.interfaces.dto.PaymentResponse
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class PaymentFacade(
    private val paymentService: PaymentService,
    private val reservationService: ReservationService,
    private val seatService: SeatService,
    private val queueService: QueueService,
    private val authUtil: AuthUtil
) {
    fun makePayment(userToken: String, queueToken: String, request: PaymentRequest): PaymentResponse {
        val userId = authUtil.getUserIdIfQueueTokenValid(userToken, queueToken)

        // 예약 정보 조회
        val reservation = reservationService.findReservationByIdAndUserId(request.reservationId, userId)
        val now = LocalDateTime.now()

        // 예약 만료 시간 확인 및 처리
        if(reservation.isExpired(now)) {
            // 예약 만료 처리
            reservationService.updateReservationStatus(reservation, ReservationStatus.CANCELLED, now)

            // 좌석 해제 처리
            seatService.releaseSeatByReservation(reservation, now)

            // 대기열에서 사용자 제거
            queueService.deleteQueueByUserId(userId)

            // 예외를 던지지 않고, 만료 상태를 나타내는 응답 반환
            return PaymentResponse(
                paymentId = null,
                status = PaymentStatus.EXPIRED,
                amount = 0,
                reservationId = reservation.id
            )
        }

        // 결제 처리
        val seatPrice = seatService.getSeatPrice(reservation.seatId)
        val payment = paymentService.makePayment(userId, request.reservationId, seatPrice)

        // 예약 상태 업데이트
        reservationService.updateReservationStatus(reservation, ReservationStatus.PAYMENT_COMPLETED, now)

        return PaymentResponse(
            paymentId = payment.id,
            status = payment.status,
            amount = payment.amount,
            reservationId = payment.reservationId
        )
    }
}