package nm.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import nm.RegistrationApplication;
import nm.domain.ClientInfoCreated;
import nm.domain.ClientInfoDeleted;
import nm.domain.ClientInfoModified;

@Entity
@Table(name = "InputInfo_table")
@Data
//<<< DDD / Aggregate Root
public class InputInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clientName;

    private Date createDate;

    @ElementCollection
    private List<Detail> details;

    @PostPersist
    public void onPostPersist() {
        ClientInfoCreated clientInfoCreated = new ClientInfoCreated(this);
        clientInfoCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        ClientInfoModified clientInfoModified = new ClientInfoModified(this);
        clientInfoModified.publishAfterCommit();
    }

    @PostRemove
    public void onPostRemove() {
        ClientInfoDeleted clientInfoDeleted = new ClientInfoDeleted(this);
        clientInfoDeleted.publishAfterCommit();
    }

    public static InputInfoRepository repository() {
        InputInfoRepository inputInfoRepository = RegistrationApplication.applicationContext.getBean(
            InputInfoRepository.class
        );
        return inputInfoRepository;
    }
}
//>>> DDD / Aggregate Root
