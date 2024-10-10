package nm.domain;

import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

@Data
@ToString
public class DnsInspected extends AbstractEvent {

    private Long ipId;
    private String obj;
    private String objDetail;
    private String state;
    private Date endedDate;
    private String resultData;
}
