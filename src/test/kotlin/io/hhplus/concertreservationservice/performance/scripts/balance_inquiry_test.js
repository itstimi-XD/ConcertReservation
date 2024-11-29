import http from 'k6/http';
import { check, sleep } from 'k6';
import { options } from '../config/k6_options.js';

export { options };

export default function () {
  const url = 'http://localhost:8080/api/users/balance';

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'User-Token': `user-${__VU}`,
    },
  };
  const res = http.get(url, params);

  // 응답 상태 코드와 본문 출력
  console.log(`Status: ${res.status}`);
  console.log(`Response Body: ${res.body}`);

  check(res, {
    'status is 200': (r) => r.status === 200,
    'transaction time OK': (r) => r.timings.duration < 300,
  });

  sleep(1);
}
