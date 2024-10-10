package nm.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

//<<< DDD / Value Object
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detail {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;
    private String obj;
    private String subobj;
    private String state;
}
//>>> DDD / Value Object
