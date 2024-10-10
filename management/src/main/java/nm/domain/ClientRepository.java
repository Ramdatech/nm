package nm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.lastInspectDate IS NULL OR c.lastInspectDate < :threshold OR c.totalReq IS NULL OR c.totalDns IS NULL OR c.totalEtn IS NULL")
    List<Client> findClientsForInspection(@Param("threshold") Date threshold);
}