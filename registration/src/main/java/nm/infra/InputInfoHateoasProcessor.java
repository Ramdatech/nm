package nm.infra;

import nm.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class InputInfoHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<InputInfo>> {

    @Override
    public EntityModel<InputInfo> process(EntityModel<InputInfo> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "//createclientinfo"
                )
                .withRel("/createclientinfo")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/modifyclientinfo"
                )
                .withRel("modifyclientinfo")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/deleteclientinfo"
                )
                .withRel("deleteclientinfo")
        );

        return model;
    }
}
