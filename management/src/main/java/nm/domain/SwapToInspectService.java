package nm.domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SwapToInspectService {

    private static final String TOPIC_NAME = "swapToInspect";

    @Autowired
    private KafkaTemplate<String, SwapToInspect> kafkaTemplate; // Kafka 메시지 전송을 위한 템플릿

    @Scheduled(fixedRate = 1000) 
    public void sendSwapToInspectEvent() {
        System.out.println("AAA");
        SwapToInspect event = new SwapToInspect();
        // event.setSomeData("필요한 데이터 설정");
        // Kafka로 이벤트 전송
        kafkaTemplate.send(TOPIC_NAME, event);

        System.out.println("SwapToInspect 이벤트가 전송되었습니다: " + event);
    }
}
