package nm.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import nm.InspectdnsApplication;
import nm.domain.DnsInspected;

@Entity
@Table(name = "DnsData_table")
@Data
//<<< DDD / Aggregate Root
public class DnsData {

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
        DnsInspected dnsInspected = new DnsInspected(this);
        dnsInspected.publishAfterCommit();
    }

    public static DnsDataRepository repository() {
        DnsDataRepository dnsDataRepository = InspectdnsApplication.applicationContext.getBean(
            DnsDataRepository.class
        );
        return dnsDataRepository;
    }

    //<<< Clean Arch / Port Method
    public static void inspect(SwapToInspect swapToInspect) {
        //implement business logic here:

        /** Example 1:  new item 
        DnsData dnsData = new DnsData();
        repository().save(dnsData);

        */

        /** Example 2:  finding and process
        
        repository().findById(swapToInspect.get???()).ifPresent(dnsData->{
            
            dnsData // do something
            repository().save(dnsData);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
