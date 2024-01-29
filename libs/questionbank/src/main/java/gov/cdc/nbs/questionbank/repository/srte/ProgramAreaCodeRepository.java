package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.ProgramAreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProgramAreaCodeRepository extends JpaRepository<ProgramAreaCode, String>, JpaSpecificationExecutor<ProgramAreaCode> {

    List<ProgramAreaCode> findAllByCodeSetNm(String codeSetNm);
}