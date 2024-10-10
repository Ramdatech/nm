package nm.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SwapToInspect extends AbstractEvent {

    private Long clientId;
    private Date createDate;
    private Date modifiDate;
    private String clientName;
    private String totalReq;
    private String totalDns;
    private String totalEtn;
    private String lastInspectDate;
    private List<Detail> details;

    public SwapToInspect(Client aggregate) {
        super(aggregate);
    }

    public SwapToInspect() {
        super();
    }
}
//>>> DDD / Domain Event
