package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.ConditionCode;
import gov.cdc.nbs.questionbank.entity.srte.ProgramAreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ConditionCodeRepository extends JpaRepository<ConditionCode, String>, JpaSpecificationExecutor<ConditionCode> {

    List<ConditionCode> findAllByConditionCodesetNm(String codeSetNm);
}