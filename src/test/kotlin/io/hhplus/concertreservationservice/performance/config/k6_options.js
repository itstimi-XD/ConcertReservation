export const options = {
  stages: [
    { duration: '1m', target: 100 }, // 1분 동안 VU를 100까지 증가
    { duration: '5m', target: 100 }, // 5분 동안 VU를 100으로 유지
    { duration: '1m', target: 0 },   // 1분 동안 VU를 0으로 감소
  ],
  thresholds: {
    http_req_duration: ['p(90)<200', 'p(95)<300', 'p(99)<500'], // 응답 시간 백분위수
    http_req_failed: ['rate<0.01'], // 에러율
  },
  // ext: {
  //   loadimpact: {
  //     projectID: YOUR_PROJECT_ID,
  //   },
  // },
};
