# 모니터링 설정

이 디렉토리는 Prometheus와 Grafana를 사용한 모니터링 설정을 포함하고 있습니다.

## 구성 요소

- `prometheus.yml`: Prometheus 설정 파일.
- `grafana/`: Grafana 대시보드 설정 파일.
- `exporters/`: Node Exporter와 JMX Exporter 설정 및 실행 파일.

## 실행 방법

1. Prometheus 실행
   ```sh
   prometheus --config.file=prometheus.yml
    ```
2.  Exporter 실행
    - Node Exporter
      ```sh
      ./exporters/node_exporter
      ```
    - JMX Exporter
      ```sh
        ./exporters/jmx_exporter
        ```
   3. Grafana 실행
       ```sh
       docker run -d -p 3000:3000 -v $(pwd)/grafana:/etc/grafana/provisioning -v $(pwd)/grafana/dashboards:/var/lib/grafana/dashboards grafana/grafana
        ```
   4. Grafana 대시보드에서 모니터링 확인