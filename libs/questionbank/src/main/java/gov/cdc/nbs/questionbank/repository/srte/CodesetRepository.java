package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.Codeset;
import gov.cdc.nbs.questionbank.entity.srte.CodesetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodesetRepository extends JpaRepository<Codeset, CodesetId>, JpaSpecificationExecutor<Codeset> {

}