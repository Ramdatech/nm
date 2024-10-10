package nm.infra;

import nm.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class HttpDataHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<HttpData>> {

    @Override
    public EntityModel<HttpData> process(EntityModel<HttpData> model) {
        return model;
    }
}
