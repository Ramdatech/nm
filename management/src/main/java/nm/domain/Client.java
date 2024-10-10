package nm.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    private Date createDate;

    private Date modifiDate;

    private String clientName;

    private String totalReq;

    private String totalDns;

    private String totalEtn;

    private Date lastInspectDate;

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
    // Ensure the clientName and other necessary fields are not null or invalid
    if (clientInfoCreated.getClientName() != null && !clientInfoCreated.getClientName().isEmpty()) {
        Client client = new Client();
        client.setClientName(clientInfoCreated.getClientName());
        client.setCreateDate(clientInfoCreated.getCreateDate());
        client.setModifiDate(clientInfoCreated.getCreateDate());
       
        // if (clientInfoCreated.getDetails() instanceof List<?>) {
        //     @SuppressWarnings("unchecked")
        //     List<Detail> detailList = (List<Detail>) clientInfoCreated.getDetails();
        //     client.setDetails(detailList); // Assuming setDetails accepts List<Detail>
        // } else {
        //     throw new IllegalArgumentException("Details must be of type List<Detail>");
        // }
        System.err.println("-------");
        System.out.println(clientInfoCreated.getDetails());
        System.err.println("-------");
        client.setDetails(clientInfoCreated.getDetails());

        repository().save(client);
    } else {
        throw new IllegalArgumentException("Client name cannot be null or empty");
    }

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void dataModify(ClientInfoModified clientInfoModified) {
    // Validate that the client ID is not null
    if (clientInfoModified.getClientId() == null) {
        throw new IllegalArgumentException("Client ID cannot be null");
    }

    // Find the client by ID
    repository().findById(clientInfoModified.getClientId()).ifPresentOrElse(client -> {
        // Update the fields based on the modified info
        if (clientInfoModified.getClientName() != null && !clientInfoModified.getClientName().isEmpty()) {
            client.setClientName(clientInfoModified.getClientName());
        }

        client.setModifiDate(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant()));
        client.setDetails(clientInfoModified.getDetails());

        // Save the modified client
        repository().save(client);
    }, () -> {
        // Handle the case where the client is not found
        throw new IllegalArgumentException("Client not found for ID: " + clientInfoModified.getClientId());
    });

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
        repository().deleteById(clientInfoDeleted.getClientId());

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
