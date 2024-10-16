# N.M

# 초안
![image](https://github.com/user-attachments/assets/68b51529-3d1d-4006-82f2-57a72d82018e)



# 서비스 시나리오

## 기능적 요구사항

---

### 1. 네트워크 장비 등록
- 고객은 네트워크 관리 페이지에서 손쉽게 장비를 등록할 수 있어야 한다.
- **입력**: 장비의 IP 주소, 포트 번호, 통신 프로토콜 (Ethernet, TCP, Serial 등)
- 등록된 장비는 실시간으로 상태를 관리하고 모니터링할 수 있도록 자동 연결된다.

### 2. 장비 상태 실시간 측정 및 모니터링
- 고객은 등록된 네트워크 장비의 상태를 실시간으로 측정할 수 있어야 한다.
- Ping, TCP 응답 등의 프로토콜을 통한 실시간 상태 측정
- 고객이 설정한 주기에 맞춰 장비의 상태가 자동으로 측정되고, 모든 정보는 시스템에 기록된다.

### 3. 측정 주기 및 조건 설정
- 고객은 장비마다 상태 측정 주기를 설정하거나 특정 조건에 따라 맞춤형 측정을 할 수 있어야 한다.

### 4. 알림 서비스
- 네트워크 상태에 이상이 발생할 경우, 고객은 즉시 알림을 받아야 한다.

### 5. 장비 관리 및 확장성
- 고객은 기존 장비의 상태를 편리하게 업데이트하거나 새로운 장비를 쉽게 추가 등록할 수 있어야 한다.
- 장비 추가 시 시스템에서 자동으로 설정하고 모니터링이 시작된다.


## 비기능적 요구사항

---

### 1. 트랜잭션
- **정확성 보장**: 네트워크 상태 측정 결과는 트랜잭션 내에서 처리되어야 하며, 장애나 오류가 발생한 경우 데이터의 일관성을 유지해야 한다. (Sync 호출)
  
### 2. 장애격리
- **측정 서비스 독립성**: 네트워크 장비의 상태 측정이 실패하더라도 전체 시스템은 24시간 365일 가동되어야 하며, 다른 기능(예: 장비 등록 및 관리)은 정상적으로 수행되어야 한다. (Async, Event-driven, Eventual Consistency)
- **회복력**: 장비 상태 측정 시스템이 과부하 상태가 되면 잠시 요청을 중단하고, 일정 시간이 지나면 재시도할 수 있어야 한다. (Circuit Breaker, Fallback)

### 3. 성능
- **실시간 조회**: 고객은 등록된 모든 장비의 상태와 실시간 데이터를 빠르게 조회할 수 있어야 한다. (CQRS)
- **알림 처리**: 네트워크 장비 상태가 변경될 때마다 즉시 고객에게 알림을 전달할 수 있어야 한다. (Event-driven)

# 분석 설계
## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  https://labs.msaez.io/#/62131378/storming/nm

### 이벤트 도출
![image](https://github.com/user-attachments/assets/625274c9-95cf-4417-8d3b-9e35b70639d9)

# 구현
## API 게이트웨이
- gateway 스프링부트 App을 추가 후 application.yaml내에 각 마이크로 서비스의 routes 를 추가하고 gateway 서버의 포트를 8088 으로 설정함

![image](https://github.com/user-attachments/assets/6e85b168-eb84-4fba-bf9a-a2268c82da4b)

## Correlation
- 비동기식 호출 / 시간적 디커플링 / 장애격리 / 최종 (Eventual) 일관성 테스트
![image](https://github.com/user-attachments/assets/39078a21-2cbd-4061-9049-18b822f73818)
