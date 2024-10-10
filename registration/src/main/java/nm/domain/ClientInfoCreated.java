package nm.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ClientInfoCreated extends AbstractEvent {

    private Long clientId;
    private String clientName;
    private Date createDate;
    private List<Detail> details;

    public ClientInfoCreated(InputInfo aggregate) {
        super(aggregate);
    }

    public ClientInfoCreated() {
        super();
    }
}
//>>> DDD / Domain Event
