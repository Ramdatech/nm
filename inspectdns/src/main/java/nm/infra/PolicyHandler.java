package nm.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import nm.config.kafka.KafkaProcessor;
import nm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    DnsDataRepository dnsDataRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SwapToInspect'"
    )
    public void wheneverSwapToInspect_Inspect(
        @Payload SwapToInspect swapToInspect
    ) {
        SwapToInspect event = swapToInspect;
        System.out.println(
            "\n\n##### listener Inspect : " + swapToInspect + "\n\n"
        );

        // Sample Logic //
        DnsData.inspect(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
