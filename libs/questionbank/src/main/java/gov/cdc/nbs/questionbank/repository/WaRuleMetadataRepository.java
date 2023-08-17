package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.WaRuleMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaRuleMetadataRepository extends JpaRepository<WaRuleMetadata, Long>, JpaSpecificationExecutor<WaRuleMetadata> {

}