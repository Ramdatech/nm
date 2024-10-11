package nm.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

@Service
public class SwapToInspectService {

    private static final String TOPIC_NAME = "SwapToInspect";

    @Autowired
    private KafkaTemplate<String, SwapToInspect> kafkaTemplate;

    @Autowired
    private ClientRepository clientRepository;  // 클라이언트 데이터베이스와 상호작용
    // management에서 이전 코드와 같이 kafka를 발송하나, inspect 레이어에서 받는 부분을 확인 필요
    @Scheduled(fixedRate = 10000)  // 10초마다 실행
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
                kafkaTemplate.send(TOPIC_NAME, event);

                System.out.println("SwapToInspect 이벤트가 전송되었습니다: " + event);
            }
        }
    }
}
