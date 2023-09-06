package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaRuleMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaRuleMetadataRepository extends JpaRepository<WaRuleMetadata, Long>, JpaSpecificationExecutor<WaRuleMetadata> {

}