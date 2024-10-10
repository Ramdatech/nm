package nm.domain;

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

    //<<< Clean Arch / Port Method
    public static void httpInspect(SwapToInspect swapToInspect) {
        //implement business logic here:

        /** Example 1:  new item 
        HttpData httpData = new HttpData();
        repository().save(httpData);

        */

        /** Example 2:  finding and process
        
        repository().findById(swapToInspect.get???()).ifPresent(httpData->{
            
            httpData // do something
            repository().save(httpData);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
