
# 쿼리 성능 개선 "시도" 보고서

## 1. 개요
이 문서는 서비스에서 자주 사용되는 쿼리에 대해 성능 개선 작업을 시도한 결과를 정리한 보고서입니다.
쿼리 실행 전후의 Explain Plan 결과와 실행 시간 비교를 통해 인덱스 추가로 인한 변화를 정리합니다.

---

## 2. 주요 쿼리 및 설명
### 2.1 특정 콘서트 일정의 잔여 좌석 조회
```sql
SELECT remaining_seats, total_seats
FROM dbkimjusubbl.concert_schedules
WHERE concert_id = 1;
```
**목적:** 콘서트 잔여 좌석 정보를 조회하여 사용자에게 제공.

---

## 3. Explain Plan 결과 비교
### 3.1 특정 콘서트 일정의 잔여 좌석 조회
#### 인덱스 추가 전
- Type: `ALL` (풀 스캔)
- Rows: 100000
- Extra: `Using where`

#### 인덱스 추가 후
- Type: `ref`
- Rows: 1
- Extra: `NULL`

**캡처: Explain Plan 비교**
- 인덱스 추가 전
  ![image](https://github.com/itstimi-XD/ConcertReservation/blob/doc/query-index-improvements/document/pic/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-11-16%20%EC%98%A4%EC%A0%84%208.48.16.png?raw=true)


- 인덱스 추가 후
  ![image](https://github.com/itstimi-XD/ConcertReservation/blob/doc/query-index-improvements/document/pic/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-11-16%20%EC%98%A4%EC%A0%84%208.53.39.png?raw=true)



---

## 4. 실행 시간 비교
### 4.1 특정 콘서트 일정의 잔여 좌석 조회
| 쿼리                              | 인덱스 추가 전 실행 시간 | 인덱스 추가 후 실행 시간 | 개선 비율    |
|-----------------------------------|----------------|----------------|----------|
| `SELECT remaining_seats ...`      | 24ms            | 26ms           | 개선 실패..! |

**캡처: 실행 시간 비교**
- 인덱스 적용 전
  ![image](https://github.com/itstimi-XD/ConcertReservation/blob/doc/query-index-improvements/document/pic/before_indexing_consert_schedule.png?raw=true)
- 인덱스 적용 후
  ![image](https://github.com/itstimi-XD/ConcertReservation/blob/doc/query-index-improvements/document/pic/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-11-16%20%EC%98%A4%EC%A0%84%208.57.22.png?raw=true)

---

## 5. 인덱스 생성 및 목적
### 5.1 특정 콘서트 일정의 잔여 좌석 조회
**추가한 인덱스:**
```sql
CREATE INDEX idx_concert_schedules_concert_id
ON dbkimjusubbl.concert_schedules (concert_id);
```

**목적:**
- `concert_id` 조건으로 조회되는 빈도가 높아 성능 개선이 필요했음.
- 풀 스캔(`ALL`)을 제거하고 효율적인 검색을 위해 인덱스를 추가함.

**캡처: 인덱스 생성**

![인덱스 생성](https://github.com/itstimi-XD/ConcertReservation/blob/doc/query-index-improvements/document/pic/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-11-16%20%EC%98%A4%EC%A0%84%208.58.18.png?raw=true)

---

## 6. 결론
- 인덱스 추가 전후 Explain Plan 결과를 통해 `Type`이 `ALL`에서 `ref`로 개선됨을 확인.
- 실행 시간은 더미 데이터 양이 충분하지 않아서인지.. 개선되지 않았음.
