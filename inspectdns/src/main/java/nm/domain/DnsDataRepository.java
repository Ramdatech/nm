package nm.domain;

import nm.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "dnsData", path = "dnsData")
public interface DnsDataRepository
    extends PagingAndSortingRepository<DnsData, Long> {}
