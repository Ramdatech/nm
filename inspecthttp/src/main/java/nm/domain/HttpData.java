package nm.domain;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import nm.InspecthttpApplication;

@Entity
@Table(name = "HttpData_table")
@Data
public class HttpData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ipId;

    private String obj;        // IP 주소 또는 타겟 대상
    private String objDetail;  // 추가 정보 (예: Sub Object)
    private String state;      // 상태 ("OK", "NG", "WN" 등)
    private Date endedDate;    // Ping 테스트가 완료된 시간
    private String resultData; // 결과 데이터 저장 (필요한 경우)

    // Kafka 이벤트 전송 메서드
    @PostPersist
    public void onPostPersist() {
        HttpInspected httpInspected = new HttpInspected(this);
        httpInspected.publishAfterCommit();
    }

    // 리포지토리 접근 메서드
    public static HttpDataRepository repository() {
        return InspecthttpApplication.applicationContext.getBean(HttpDataRepository.class);
    }

    // Ping 테스트를 수행하는 유틸리티 메서드
    public static boolean pingHost(String ipAddress, int timeout) throws IOException {
        return InetAddress.getByName(ipAddress).isReachable(timeout);  // Ping 테스트 수행
    }

    // httpInspect 메서드 - ping 테스트 결과를 HttpData에 반영
    public static void httpInspect(SwapToInspect swapToInspect) {
        repository().findById(Long.valueOf(swapToInspect.getClientId())).ifPresent(httpData -> {

            // details가 null이 아닌지 확인
            if (swapToInspect.getDetails() != null) {
                for (Detail detail : swapToInspect.getDetails()) {
                    // detail의 type이 "http"인 경우
                    if ("http".equals(detail.getType())) {
                        httpData.setObj(detail.getObj());           // IP 설정
                        httpData.setObjDetail(detail.getSubobj());  // Sub Object 설정
                        httpData.setState(detail.getState());       // 초기 상태 설정
                        httpData.setEndedDate(new Date());          // 완료 날짜 설정

                        String ipAddress = detail.getObj();
                        int timeout = 1000;

                        // Ping 테스트 수행
                        try {
                            boolean status = pingHost(ipAddress, timeout);
                            if (status) {
                                System.out.println(ipAddress + " is reachable.");
                                httpData.setState("OK");  // Ping 성공 시 상태를 "OK"로 설정
                            } else {
                                System.out.println(ipAddress + " is not reachable.");
                                httpData.setState("NG");  // Ping 실패 시 상태를 "NG"로 설정
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Ping 검사 중 오류 발생: " + e.getMessage());
                            httpData.setState("WN");  // Ping 오류 발생 시 "WN"으로 설정
                        }

                        // 데이터 저장
                        repository().save(httpData);
                    }
                }
            }
        });
    }
}
