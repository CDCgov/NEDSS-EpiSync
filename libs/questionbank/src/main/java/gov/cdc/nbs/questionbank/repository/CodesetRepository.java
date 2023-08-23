package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodesetRepository extends JpaRepository<Codeset, CodesetId>, JpaSpecificationExecutor<Codeset> {

}