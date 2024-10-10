package nm.domain;

import java.util.*;
import lombok.*;
import nm.domain.*;
import nm.infra.AbstractEvent;

@Data
@ToString
public class SwapToInspect extends AbstractEvent {

    private Long id;
    private String createDate;
    private String modifiDate;
    private String clientName;
    private String totalReq;
    private String totalDns;
    private String totalEtn;
    private String lastInspectDate;
    private List<Detail> details;
}
