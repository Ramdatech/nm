# N Linkus

# 초기 구상
![image](https://github.com/user-attachments/assets/e7210a6c-74b8-4f68-8168-4cc026ce7162)

고객별로 보유하거나 관제를 원하는 다양한 프로토콜 및 정보에 대해, <br>
MSA 기반의 모니터링 시스템을 구축하고 시계열로 데이터를 보관하는 플랫폼


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

## Scheduling
```

@Service
public class SwapToInspectService {

    private static final String TOPIC_NAME = "nm";

    @Autowired
    private KafkaTemplate<String, SwapToInspect> kafkaTemplate;

    @Autowired
    private ClientRepository clientRepository;  // 클라이언트 데이터베이스와 상호작용
    @Scheduled(fixedRate = 3000)
    public void sendSwapToInspectEvent() {
        // 현재 시점에서 5분 전의 시간을 구함
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        Date threshold = cal.getTime();

        // 조건에 맞는 클라이언트를 찾아온다
        List<Client> clientsToInspect = clientRepository.findClientsForInspection(threshold);

        if (clientsToInspect.isEmpty()) {
            System.out.println("검사 대상 클라이언트가 없습니다.");
        } else {
            // 조건에 맞는 각 클라이언트에 대해 SwapToInspect 이벤트 전송
            for (Client client : clientsToInspect) {
                SwapToInspect event = new SwapToInspect();
                event.setClientId(client.getClientId());  // 클라이언트 ID 설정
                event.setClientName(client.getClientName());  // 클라이언트 이름 설정
                event.setCreateDate(client.getCreateDate());  // 클라이언트 생성일 설정
                event.setModifiDate(client.getModifiDate());  // 수정일 설정
                event.setTotalReq(client.getTotalReq());  // TotalReq 설정
                event.setTotalDns(client.getTotalDns());  // TotalDns 설정
                event.setTotalEtn(client.getTotalEtn());  // TotalEtn 설정
                event.setDetails(client.getDetails());  // 클라이언트의 상세 정보 설정
    

                // Kafka로 이벤트 전송
                // kafkaTemplate.send(TOPIC_NAME, event);

                // Kafka 메시지 생성 (헤더 포함)
                ProducerRecord<String, SwapToInspect> record = new ProducerRecord<>(TOPIC_NAME, event);

                // 헤더에 'type'을 UTF-8로 인코딩하여 추가
                record.headers().add(new RecordHeader("type", "SwapToInspect".getBytes(StandardCharsets.UTF_8)));

                // Kafka로 이벤트 전송
                kafkaTemplate.send(record).addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onSuccess(SendResult<String, SwapToInspect> result) {
                        System.out.println("Message sent successfully: " + result.getProducerRecord().value());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        System.err.println("Message failed to send: " + ex.getMessage());
                    }
                });
                System.out.println("SwapToInspect 이벤트가 전송되었습니다: " + event);
            }
        }
    }
}

```
### 조건 : 응답결과 집계가 없거나, 최종 측정일로부터 특정 시간이 경과된 경우<br><br>
  **분기 1 ) 본 쿼리에 해당하는 데이터가 존재하지 않을 때<br><br>**
  ![image](https://github.com/user-attachments/assets/c425aa1f-a05a-4f8e-860b-5295c5a7475b)<br>
  **분기 2 ) 데이터가 존재할 경우<br><br>**
  ![image](https://github.com/user-attachments/assets/76cc18ca-9933-4f13-9c4f-5c0270809d59)<br>


## ICMP 응답 측정
```
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type'] == 'SwapToInspect'"
    )
    public void wheneverSwapToInspect_HttpInspect(
        @Payload SwapToInspect swapToInspect,
        @Header("type") byte[] typeHeader // 바이트 배열로 수신됨
    ) {
        if (typeHeader != null) {
            String type = new String(typeHeader, StandardCharsets.UTF_8);
            System.out.println("Received type: " + type);
    
            if ("SwapToInspect".equals(type)) {
                System.out.println("Received event: " + swapToInspect);
                HttpData.httpInspect(swapToInspect);  // Ping 테스트 수행
            } else {
                System.out.println("Received unknown type: " + type);
            }
        } else {
            System.out.println("Type header is missing");
        }
    }
```
![image](https://github.com/user-attachments/assets/bcf370e0-b4dd-4faa-a1d3-10ddcbf87564)

## 정보 등록 API
### 일대다 관계 (고객 - 장비 간)
![image](https://github.com/user-attachments/assets/1300e215-ddd4-4896-97a8-5e0bcea3adfb)
<br>
```
### inputs
clientName='dd' details:='[ { "id": 5, "type": "dns", "obj": "192.127.0.5", "subobj": "", "state": "" }, { "id": 7, "type": "icmp", "obj": "192.168.0.5", "subobj": "", "state": "" } ]'
```
```
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;

    private Date createDate;

    private Date modifiDate;

    private String clientName;

    private String totalReq;

    private String totalDns;

    private String totalEtn;

    private Date lastInspectDate;

    // @ElementCollection
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Detail> details;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String type;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String obj;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String subobj;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String state;
}
```
![image](https://github.com/user-attachments/assets/995e1a5e-843b-4efd-b0e5-893f5424aa04)



## 테스트 페이지
![image](https://github.com/user-attachments/assets/1f19110b-2fe6-4cd2-9296-eb90cb34812e)
![image](https://github.com/user-attachments/assets/0d8532bc-eca4-4aa9-9f12-b492bfb73f49)



## API 게이트웨이
- gateway 스프링부트 App을 추가 후 application.yaml내에 각 마이크로 서비스의 routes 를 추가하고 gateway 서버의 포트를 8088 으로 설정함

![image](https://github.com/user-attachments/assets/6e85b168-eb84-4fba-bf9a-a2268c82da4b)

## Correlation
- 비동기식 호출 / 시간적 디커플링 / 장애격리 / 최종 (Eventual) 일관성 테스트
![image](https://github.com/user-attachments/assets/39078a21-2cbd-4061-9049-18b822f73818)
