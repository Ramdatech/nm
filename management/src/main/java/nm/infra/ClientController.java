package nm.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import nm.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
// @RequestMapping(value="/clients")
@Transactional
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    private Random random = new Random();

    // 단순 GET 요청을 처리하는 메서드
    @GetMapping("/api/client/addDummyData")
    public String addDummyData() {
        // 랜덤 클라이언트 생성
        Client client = new Client();
        client.setClientName("Client-" + random.nextInt(1000));

        // TotalReq, TotalDns, TotalEtn 필드들은 null로 설정
        client.setTotalReq(null);
        client.setTotalDns(null);
        client.setTotalEtn(null);
        client.setLastInspectDate(null);  // 검사 날짜는 null로 초기화

        // 랜덤한 Detail 리스트 생성
        List<Detail> details = new ArrayList<>();
        Detail detail = new Detail();
        
        // type은 'icmp'로 고정
        detail.setType("icmp");

        // 랜덤 IP 주소 생성
        detail.setObj(generateRandomIp());

        // subobj는 빈 문자열
        detail.setSubobj("");

        // state는 null로 설정
        detail.setState(null);
        
        // Detail 객체를 리스트에 추가
        details.add(detail);

        client.setDetails(details);

        // 데이터베이스에 저장
        clientRepository.save(client);

        return "더미 데이터가 성공적으로 추가되었습니다: " + client.getClientName();
    }

    // 랜덤 IP 주소를 생성하는 메서드
    private String generateRandomIp() {
        return random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256) + "." + random.nextInt(256);
    }
}