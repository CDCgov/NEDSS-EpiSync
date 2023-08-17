package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.WaNndMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaNndMetadataRepository extends JpaRepository<WaNndMetadata, Long>, JpaSpecificationExecutor<WaNndMetadata> {

}