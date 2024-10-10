package nm.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class IcmpInspected extends AbstractEvent {

    private Long ipId;
    private String obj;
    private String objDetail;
    private String state;
    private Date endedDate;
    private String resultData;

    public IcmpInspected(IcmpData aggregate) {
        super(aggregate);
    }

    public IcmpInspected() {
        super();
    }
}
//>>> DDD / Domain Event
