
# 서비스 설계 문서

## 1. 트랜잭션 범위 이해

### 1.1 트랜잭션 범위
현재 제 서비스에서는 Spring Boot와 Spring Data JPA를 사용하여 트랜잭션을 관리하고 있으며, 트랜잭션 범위는 다음과 같습니다:
1. **유저 토큰 발급**: 유저 정보 저장 및 토큰 생성.
2. **잔액 충전**: 유저의 잔액 업데이트.
3. **좌석 예약**: 좌석 상태 업데이트 및 예약 정보 저장.
4. **결제 처리**: 결제 정보 저장 및 잔액 차감.

---

## 2. 서비스 확장 시 설계

### 2.1 서비스 분리
서비스 확장으로 인해 각 도메인이 독립적인 서비스로 분리될 수 있습니다:
- **Auth 서비스**: 인증 및 유저 토큰 관리.
- **Balance 서비스**: 잔액 충전 및 거래 기록.
- **Reservation 서비스**: 좌석 예약, 대기열 관리.
- **Payment 서비스**: 결제 처리 및 정산.

### 2.2 트랜잭션 처리의 한계
서비스를 분리하면 다음과 같은 한계가 발생할 수 있습니다:
1. **데이터 일관성 문제**:
   - 서비스 간 데이터를 조정하는 과정에서 **분산 트랜잭션** 문제가 발생할 수 있습니다.
2. **트랜잭션 관리**:
   - 기존의 단일 트랜잭션을 다중 서비스로 확장할 경우, 트랜잭션 간의 조율이 필요.

### 2.3 해결 방안
1. **SAGA 패턴**:
   - 각 서비스의 로컬 트랜잭션을 독립적으로 관리하고, 실패 시 보상 트랜잭션 실행.
2. **이벤트 기반 아키텍처**:
   - 상태 변경을 이벤트로 저장하고, 이벤트를 통해 서비스 간 동기화를 유지.
3. **데이터베이스 공유**:
   - 초기 단계에서는 서비스 간 데이터를 공유하는 데이터베이스를 유지하여 트랜잭션 관리 복잡성을 줄임.

---

## 3. 실시간 정보 전달과 이력 저장

### 3.1 요구사항
1. 실시간 좌석 예약 정보를 외부 시스템에 전달.
2. 예약 및 결제 정보를 데이터 플랫폼에 메시지 발행 방식으로 전달.

### 3.2 설계
- **외부 API 호출과 메시지 발행을 분리하여 독립적인 트랜잭션 관리**:
  - 데이터베이스 작업(좌석 예약 등)은 기본 트랜잭션에서 처리.
  - 외부 시스템 호출과 메시지 발행은 별도 트랜잭션으로 처리.

---

## 4. 코드 개선 예제

### 4.1 트랜잭션 분리 예제

```kotlin
@Service
class ReservationFacade(
    private val reservationService: ReservationService,
    private val eventPublisher: ReservationEventPublisher // 메시지 발행기
) {
    @Transactional
    fun reserveSeat(userToken: String, concertScheduleId: Long, seatNumber: Int) {
        // 좌석 예약 처리 (로컬 트랜잭션)
        reservationService.reserveSeat(userToken, concertScheduleId, seatNumber)
        
        // 이벤트 발행 (별도 비동기 처리)
        eventPublisher.publishReservationEvent(userToken, concertScheduleId, seatNumber)
    }
}
```

---

### 4.2 이벤트 발행기 구현

```kotlin
@Component
class ReservationEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun publishReservationEvent(userToken: String, concertScheduleId: Long, seatNumber: Int) {
        val reservationInfo = mapOf(
            "userToken" to userToken,
            "concertScheduleId" to concertScheduleId,
            "seatNumber" to seatNumber
        )
        kafkaTemplate.send("reservation-topic", reservationInfo.toString())
    }
}
```

---

### 4.3 외부 API 호출 분리

```kotlin
@Component
class ExternalApiNotifier(
    private val restTemplate: RestTemplate
) {
    fun notifyExternalApi(reservationInfo: Map<String, Any>) {
        try {
            restTemplate.postForObject("http://external-api.com/reservations", reservationInfo, String::class.java)
        } catch (ex: Exception) {
            // 로그 작성 및 오류 처리
            println("External API 호출 실패: ${ex.message}")
        }
    }
}
```

---

### 4.4 비동기 이벤트 처리

```kotlin
@Service
class AsyncReservationEventHandler(
    private val externalApiNotifier: ExternalApiNotifier
) {
    @KafkaListener(topics = ["reservation-topic"], groupId = "reservation-group")
    fun handleReservationEvent(message: String) {
        val reservationInfo = ObjectMapper().readValue(message, Map::class.java)
        externalApiNotifier.notifyExternalApi(reservationInfo as Map<String, Any>)
    }
}
```

---

## 5. 기존 로직에 영향 없이 부가 기능 제공
- 외부 API 호출과 메시지 발행은 기존 트랜잭션과 분리되어 동작.
- Kafka 등 비동기 메시지 큐를 활용하여 실시간 정보를 전달.
- 실패 시 로컬 트랜잭션에는 영향을 주지 않도록 설계.

---

## 결론
- 서비스 확장 시 발생할 수 있는 트랜잭션 처리 문제를 SAGA 패턴과 이벤트 기반 아키텍처를 통해 해결할 수 있습니다.
- 외부 시스템 연동은 기존 트랜잭션과 분리하여 안정성을 확보합니다.
