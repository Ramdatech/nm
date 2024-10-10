package nm.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import nm.ManagementApplication;
import nm.domain.SwapToInspect;

@Entity
@Table(name = "Client_table")
@Data
//<<< DDD / Aggregate Root
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;

    private String createDate;

    private String modifiDate;

    private String clientName;

    private String totalReq;

    private String totalDns;

    private String totalEtn;

    private String lastInspectDate;

    @ElementCollection
    private List<Detail> details;

    @PostPersist
    public void onPostPersist() {
        SwapToInspect swapToInspect = new SwapToInspect(this);
        swapToInspect.publishAfterCommit();
    }

    public static ClientRepository repository() {
        ClientRepository clientRepository = ManagementApplication.applicationContext.getBean(
            ClientRepository.class
        );
        return clientRepository;
    }

    //<<< Clean Arch / Port Method
    public static void dataRegist(ClientInfoCreated clientInfoCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(clientInfoCreated.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataModify(ClientInfoModified clientInfoModified) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(clientInfoModified.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataModify(IcmpInspected icmpInspected) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(icmpInspected.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataModify(HttpInspected httpInspected) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(httpInspected.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataModify(DnsInspected dnsInspected) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(dnsInspected.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataDelete(ClientInfoDeleted clientInfoDeleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Client client = new Client();
        repository().save(client);

        */

        /** Example 2:  finding and process
        
        repository().findById(clientInfoDeleted.get???()).ifPresent(client->{
            
            client // do something
            repository().save(client);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
