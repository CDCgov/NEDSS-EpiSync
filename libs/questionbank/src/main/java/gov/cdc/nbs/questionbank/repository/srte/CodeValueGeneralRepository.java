package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneralId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeValueGeneralRepository extends JpaRepository<CodeValueGeneral, CodeValueGeneralId>, JpaSpecificationExecutor<CodeValueGeneral> {

}