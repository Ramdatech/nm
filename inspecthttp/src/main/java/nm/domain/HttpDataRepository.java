package nm.domain;

import nm.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "httpData", path = "httpData")
public interface HttpDataRepository
    extends PagingAndSortingRepository<HttpData, Long> {}
