# **Grafana를 실행하고 설정하는 방법**

---

## **목차**

1. [Grafana 설치 확인 및 실행](#1-grafana-설치-확인-및-실행)
2. [Grafana에 접속하기](#2-grafana에-접속하기)
3. [Grafana에서 데이터 소스 추가](#3-grafana에서-데이터-소스-추가)
4. [Grafana에서 대시보드 가져오기](#4-grafana에서-대시보드-가져오기)
5. [부하 테스트 실행 및 Grafana에서 모니터링](#5-부하-테스트-실행-및-grafana에서-모니터링)
6. [추가 설정 및 팁](#6-추가-설정-및-팁)

---

## **1. Grafana 설치 확인 및 실행**

### **1.1 Grafana 설치 확인**

이전에 Homebrew를 통해 Grafana를 설치하셨을 것입니다. 설치 여부를 확인하기 위해 다음 명령어를 실행해보세요:

```bash
brew list | grep grafana
```

- 만약 `grafana`가 목록에 표시되지 않는다면, 다음 명령어로 설치하세요:

```bash
brew install grafana
```

### **1.2 Grafana 실행**

Grafana를 실행하는 방법은 두 가지가 있습니다: **직접 실행**과 **서비스로 실행**.

#### **1.2.1 직접 실행**

터미널에서 다음 명령어를 실행하여 Grafana를 직접 실행할 수 있습니다:

```bash
grafana-server
```

- 이 경우, 터미널 창이 Grafana의 로그를 출력하며 종료하지 않고 계속 열려 있어야 합니다.

#### **1.2.2 백그라운드 서비스로 실행**

Homebrew를 사용하여 Grafana를 macOS 서비스로 실행하면 백그라운드에서 실행됩니다:

```bash
brew services start grafana
```

- 이렇게 하면 시스템 재부팅 시에도 Grafana가 자동으로 실행됩니다.

**실행 상태 확인**:

```bash
brew services list | grep grafana
```

- `started` 상태로 표시되면 정상적으로 실행 중입니다.

---

## **2. Grafana에 접속하기**

### **2.1 웹 브라우저에서 Grafana 접속**

- 웹 브라우저를 열고 주소창에 다음을 입력합니다:

```
http://localhost:3000
```

- 기본적으로 Grafana는 **포트 3000**에서 실행됩니다.

### **2.2 Grafana 로그인**

- **초기 로그인 정보**:

    - **아이디(ID)**: `admin`
    - **비밀번호(Password)**: `admin`

- 첫 로그인 시 비밀번호 변경을 요청받을 수 있습니다. 새로운 비밀번호를 설정하세요.

---

## **3. Grafana에서 데이터 소스 추가**

Grafana에서 Prometheus를 데이터 소스로 추가하여 메트릭을 시각화할 수 있습니다.

### **3.1 데이터 소스 추가**

1. **Grafana 홈 화면**에서 좌측 사이드바의 **기어 아이콘 (Configuration)**을 클릭하고 **"Data Sources"**를 선택합니다.

2. **"Add data source"** 버튼을 클릭합니다.

3. 데이터 소스 목록에서 **"Prometheus"**를 선택합니다.

### **3.2 데이터 소스 설정**

- **URL** 설정:

    - **URL**: `http://localhost:9090`

- **기타 설정**은 기본값을 그대로 사용하시면 됩니다.

### **3.3 데이터 소스 연결 테스트**

- 하단의 **"Save & Test"** 버튼을 클릭하여 데이터 소스가 정상적으로 연결되었는지 확인합니다.

- **"Data source is working"** 메시지가 표시되면 성공입니다.

---

## **4. Grafana에서 대시보드 가져오기**

부하 테스트 결과와 시스템 리소스 사용량을 시각화하기 위해 대시보드를 가져옵니다.

### **4.1 k6 Load Testing Results 대시보드 가져오기**

1. 좌측 사이드바의 **"+" 아이콘 (Create)**을 클릭하고 **"Import"**를 선택합니다.

2. **"Import via grafana.com"** 섹션에 다음 **Dashboard ID**를 입력합니다:

    - **Dashboard ID**: `2587`

3. **"Load"** 버튼을 클릭합니다.

4. **대시보드 설정**:

    - **Prometheus** 데이터 소스를 선택합니다.

5. **"Import"** 버튼을 클릭하여 대시보드를 가져옵니다.

- 이제 **k6 Load Testing Results** 대시보드가 생성되었습니다.

### **4.2 Node Exporter Full 대시보드 가져오기**

1. 다시 **"Import"** 페이지로 이동합니다.

2. **Dashboard ID**로 다음 값을 입력합니다:

    - **Dashboard ID**: `1860`

3. **"Load"** 버튼을 클릭합니다.

4. **대시보드 설정**:

    - **Prometheus** 데이터 소스를 선택합니다.

5. **"Import"** 버튼을 클릭하여 대시보드를 가져옵니다.

- 이제 **Node Exporter Full** 대시보드가 생성되었습니다.

---

## **5. 부하 테스트 실행 및 Grafana에서 모니터링**

### **5.1 부하 테스트 실행**

부하 테스트를 실행하여 메트릭을 수집합니다.

#### **5.1.1 k6 실행**

터미널에서 다음 명령어를 실행하여 k6 부하 테스트를 시작합니다:

```bash
k6 run --out prometheus scripts/balance_inquiry_test.js
```

- `--out prometheus` 옵션을 사용하여 k6가 Prometheus로 메트릭을 노출하도록 설정합니다.

#### **5.1.2 Prometheus 설정 확인**

- `prometheus.yml` 파일에 k6 타겟이 포함되어 있는지 확인합니다:

```yaml
scrape_configs:
  - job_name: 'k6'
    static_configs:
      - targets: ['localhost:6565']
```

- k6는 기본적으로 **포트 6565**에서 메트릭을 노출합니다.

- Prometheus가 실행 중인지 확인하고, 필요하다면 다음 명령어로 실행합니다:

```bash
prometheus --config.file=./prometheus.yml
```

### **5.2 Grafana에서 부하 테스트 결과 모니터링**

#### **5.2.1 k6 대시보드 확인**

1. Grafana에서 **"Dashboards"** > **"Manage"**를 클릭하고 **"k6 Load Testing Results"** 대시보드를 엽니다.

2. 부하 테스트 진행 중에 실시간으로 메트릭이 표시됩니다.

    - **응답 시간**, **에러율**, **VU 수** 등의 지표를 확인할 수 있습니다.

#### **5.2.2 Node Exporter 대시보드 확인**

1. **"Dashboards"** > **"Manage"**를 클릭하고 **"Node Exporter Full"** 대시보드를 엽니다.

2. 시스템 리소스 사용량을 실시간으로 모니터링합니다.

    - **CPU 사용률**, **메모리 사용량**, **네트워크 I/O** 등의 지표를 확인할 수 있습니다.

### **5.3 테스트 결과 분석**

- 부하 테스트가 진행되는 동안 Grafana 대시보드를 통해 시스템의 상태를 모니터링하고, 필요한 경우 데이터를 저장하여 나중에 분석할 수 있습니다.

- 테스트 종료 후에도 Grafana에서 수집된 메트릭을 기반으로 성능 지표를 분석합니다.

---

## **6. 추가 설정 및 팁**

### **6.1 Grafana 대시보드 시간 범위 설정**

- 대시보드 우측 상단의 시간 범위를 조정하여 원하는 기간의 데이터를 조회할 수 있습니다.

    - 예: **Last 15 minutes**, **Last 1 hour**, **Custom Range** 등

### **6.2 대시보드 자동 새로 고침 설정**

- 대시보드 우측 상단의 **자동 새로 고침** 옵션을 설정하여 일정한 간격으로 대시보드가 업데이트되도록 할 수 있습니다.

    - 예: **Refresh every 5s**, **10s**, **30s** 등

### **6.3 사용자 정의 패널 추가**

- 필요한 경우 대시보드에 새로운 패널을 추가하여 특정 메트릭을 시각화할 수 있습니다.

    - **"Add panel"** 버튼을 클릭하여 새로운 패널을 생성하고, 쿼리를 설정합니다.

### **6.4 알림 설정 (선택 사항)**

- Grafana와 Prometheus를 연동하여 특정 임계값을 초과할 때 알림을 받을 수 있습니다.

    - **Alerting** 기능을 활용하여 알림 규칙을 설정합니다.

### **6.5 Grafana 플러그인 설치 (선택 사항)**

- 더 다양한 시각화를 위해 Grafana 플러그인을 설치할 수 있습니다.

    - 터미널에서 다음 명령어를 실행하여 플러그인을 설치합니다:

      ```bash
      grafana-cli plugins install <plugin-name>
      ```

    - 설치 후 Grafana를 재시작해야 합니다.

합니다!