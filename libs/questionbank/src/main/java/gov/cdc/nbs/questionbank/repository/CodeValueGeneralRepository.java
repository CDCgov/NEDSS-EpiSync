package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodeValueGeneralRepository extends JpaRepository<CodeValueGeneral, String>, JpaSpecificationExecutor<CodeValueGeneral> {

}