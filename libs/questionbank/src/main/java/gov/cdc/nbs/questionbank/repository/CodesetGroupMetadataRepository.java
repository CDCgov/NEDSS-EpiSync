package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.CodesetGroupMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodesetGroupMetadataRepository extends JpaRepository<CodesetGroupMetadata, Long>, JpaSpecificationExecutor<CodesetGroupMetadata> {

}