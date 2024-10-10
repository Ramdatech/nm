package nm.domain;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import nm.InspecthttpApplication;
import nm.domain.HttpInspected;

@Entity
@Table(name = "HttpData_table")
@Data
//<<< DDD / Aggregate Root
public class HttpData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ipId;

    private String obj;

    private String objDetail;

    private String state;

    private Date endedDate;

    private String resultData;

    @PostPersist
    public void onPostPersist() {
        HttpInspected httpInspected = new HttpInspected(this);
        httpInspected.publishAfterCommit();
    }

    public static HttpDataRepository repository() {
        HttpDataRepository httpDataRepository = InspecthttpApplication.applicationContext.getBean(
            HttpDataRepository.class
        );
        return httpDataRepository;
    }

    public static boolean pingHost(String ipAddress, int timeout) throws IOException {
        return InetAddress.getByName(ipAddress).isReachable(timeout);
    }

    //<<< Clean Arch / Port Method
    public static void httpInspect(SwapToInspect swapToInspect) {
        
        repository().findById(Long.valueOf(swapToInspect.getClientId())).ifPresent(icmpData->{

            // details가 null이 아닌지 확인
            if (swapToInspect.getDetails() != null) {
                // swapToInspect.getDetails() 리스트 순회
                for (Detail detail : swapToInspect.getDetails()) {
                    // detail의 type이 "http"인 경우
                    if ("http".equals(detail.getType())) {
                        icmpData.setObj(detail.getObj());
                        icmpData.setObjDetail(detail.getSubobj());
                        icmpData.setState(detail.getState());
                        icmpData.setEndedDate(new Date());
        
                        String ipAddress = detail.getObj();
                        int timeout = 1000;
                        try {
                            boolean status = pingHost(ipAddress, timeout);
                            if (status) {
                                System.out.println(ipAddress + " is reachable.");
                                icmpData.setState("OK");
                            } else {
                                System.out.println(ipAddress + " is not reachable.");
                                icmpData.setState("NG");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Ping 검사 중 오류 발생: " + e.getMessage());
                            icmpData.setState("WN");
                        }
        
                        // 데이터 저장
                        repository().save(icmpData);
                    }
                }
            }
        });
    }
}
