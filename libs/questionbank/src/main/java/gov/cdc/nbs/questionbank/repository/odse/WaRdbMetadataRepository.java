package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaRdbMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaRdbMetadataRepository extends JpaRepository<WaRdbMetadata, Long>, JpaSpecificationExecutor<WaRdbMetadata> {

}