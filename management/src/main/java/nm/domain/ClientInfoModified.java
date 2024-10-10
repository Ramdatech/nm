package nm.domain;

import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

@Data
@ToString
public class ClientInfoModified extends AbstractEvent {

    private Long clientId;
    private String clientName;
    private Date createDate;
    private List<Detail> details;
}
