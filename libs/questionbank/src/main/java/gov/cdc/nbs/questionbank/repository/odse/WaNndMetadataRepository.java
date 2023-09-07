package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaNndMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaNndMetadataRepository extends JpaRepository<WaNndMetadata, Long>, JpaSpecificationExecutor<WaNndMetadata> {

}