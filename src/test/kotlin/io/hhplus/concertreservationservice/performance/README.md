# 성능 테스트 스크립트

이 디렉토리는 k6를 사용한 부하 테스트 스크립트를 포함하고 있습니다.

## 구조

- `scripts/`: 각 시나리오별 테스트 스크립트
- `config/`: 공통 설정 파일
- `results/`: 테스트 결과 저장 디렉토리

## 실행 방법

1. k6 설치
   ```sh
   brew install k6  # macOS
   sudo apt-get install k6  # Ubuntu
    ```
2. 테스트 스크립트 실행

   ```sh
    k6 run scripts/recharge_test.js
   ```
   
3. 결과 확인

- results/ 디렉토리에서 결과 파일 확인
- Grafana 대시보드에서 실시간 모니터링