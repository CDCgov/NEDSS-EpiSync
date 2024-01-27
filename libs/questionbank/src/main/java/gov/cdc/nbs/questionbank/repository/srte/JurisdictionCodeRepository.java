package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.JurisdictionCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JurisdictionCodeRepository extends JpaRepository<JurisdictionCode, String>, JpaSpecificationExecutor<JurisdictionCode> {

    List<JurisdictionCode> findAllByCodeSetNm(String codeSetNm);
}