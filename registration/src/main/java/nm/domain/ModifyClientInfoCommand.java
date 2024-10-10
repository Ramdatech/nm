package nm.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ModifyClientInfoCommand {

    private Long clientId;
    private String clientName;
    private Date createDate;
    private List<Detail> details;
}
