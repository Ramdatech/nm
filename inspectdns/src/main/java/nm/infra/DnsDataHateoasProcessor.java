package nm.infra;

import nm.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class DnsDataHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<DnsData>> {

    @Override
    public EntityModel<DnsData> process(EntityModel<DnsData> model) {
        return model;
    }
}
