import http from 'k6/http';
import { check, sleep } from 'k6';
import { options } from '../config/k6_options.js';

export { options };

export default function () {
  const url = 'http://localhost:8080/api/balance/inquiry';

  const params = {
    headers: {
      'Content-Type': 'application/json',
      'userToken': 'user-{__VU}',
      'queueToken': 'sampleQueueToken'
    },
  };
  const res = http.get(url, params);
  check(res, {
    'status is 200': (r) => r.status === 200,
    'transaction time OK': (r) => r.timings.duration < 300,
  });

  sleep(1);
}
