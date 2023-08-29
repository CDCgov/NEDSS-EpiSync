package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaUiMetadataRepository extends JpaRepository<WaUiMetadata, Long>, JpaSpecificationExecutor<WaUiMetadata> {

}