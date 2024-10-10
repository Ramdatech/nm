package nm.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class DnsInspected extends AbstractEvent {

    private Long id;
    private String obj;
    private String objDetail;
    private String state;
    private Date endedDate;
    private String resultData;

    public DnsInspected(DnsData aggregate) {
        super(aggregate);
    }

    public DnsInspected() {
        super();
    }
}
//>>> DDD / Domain Event
