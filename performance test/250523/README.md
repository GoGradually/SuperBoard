# 테스트 시나리오

- 5분간 진행
- 2000명의 VU가 2초~10초의 간격으로 요청 송신
    - 사용자가 2초~10초동안 게시글 목록을 확인하고, 다음 페이지로 넘어간다고 가정

# 테스트 결과

구글의 SRE 조직에서 정의한, 4개의 Golden Signal 중 2가지인 TPS 와 P95 Response time 에 대해 평가한다.

```json
{
  "HTTP 요청": {
    "transaction": "HTTP 요청",
    "sampleCount": 96891,
    "errorCount": 0,
    "errorPct": 0.0,
    "meanResTime": 139.1925359424516,
    "medianResTime": 92.0,
    "minResTime": 13.0,
    "maxResTime": 1957.0,
    "pct1ResTime": 197.0,
    "pct2ResTime": 596.9500000000007,
    "pct3ResTime": 668.9900000000016,
    "throughput": 325.02851392150285,
    "receivedKBytesPerSec": 1091.2578426387956,
    "sentKBytesPerSec": 80.62230716412277
  },
  "Total": {
    "transaction": "Total",
    "sampleCount": 290673,
    "errorCount": 0,
    "errorPct": 0.0,
    "meanResTime": 92.77804956084728,
    "medianResTime": 18.0,
    "minResTime": 5.0,
    "maxResTime": 1957.0,
    "pct1ResTime": 108.0,
    "pct2ResTime": 117.0,
    "pct3ResTime": 142.0,
    "throughput": 975.0855417645085,
    "receivedKBytesPerSec": 2182.5156852775913,
    "sentKBytesPerSec": 161.24461432824555
  },
  "HTTP 요청-1": {
    "transaction": "HTTP 요청-1",
    "sampleCount": 96891,
    "errorCount": 0,
    "errorPct": 0.0,
    "meanResTime": 14.257598744981582,
    "medianResTime": 10.0,
    "minResTime": 5.0,
    "maxResTime": 501.0,
    "pct1ResTime": 22.0,
    "pct2ResTime": 26.0,
    "pct3ResTime": 56.9900000000016,
    "throughput": 325.21506801956167,
    "receivedKBytesPerSec": 966.1174188627996,
    "sentKBytesPerSec": 40.33429066258236
  },
  "HTTP 요청-0": {
    "transaction": "HTTP 요청-0",
    "sampleCount": 96891,
    "errorCount": 0,
    "errorPct": 0.0,
    "meanResTime": 124.88401399510761,
    "medianResTime": 83.0,
    "minResTime": 7.0,
    "maxResTime": 1845.0,
    "pct1ResTime": 184.0,
    "pct2ResTime": 565.9500000000007,
    "pct3ResTime": 640.0,
    "throughput": 325.0383272222241,
    "receivedKBytesPerSec": 125.69841560546946,
    "sentKBytesPerSec": 40.31237066135006
  }
}
```

## P95 Response Time

- HTTP 요청-0
    - 리버스 프록시 Nginx의 리다이렉션 요청
    - 565ms로, 응답에 긴 시간이 소요되는 중
- HTTP 요청-1
    - 26ms로, 무리없이 소화해내는 중
    - 해당 시나리오에 대해, 성능상 병목이 발생하지 않음

**Nginx의 리다이렉션 병목 문제를 해결해야 함.**

## TPS

- 총합 TPS: 325/sec
- 총 96891개의 요청이 5분(300초)간 처리되었다고 봤을 때, 충분히 준수한 TPS 임.