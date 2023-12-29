package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneralId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface CodeValueGeneralRepository extends JpaRepository<CodeValueGeneral, CodeValueGeneralId>, JpaSpecificationExecutor<CodeValueGeneral> {

    List<CodeValueGeneral> findAllByCodeSetNmIsIn(Collection<String> codes);
}