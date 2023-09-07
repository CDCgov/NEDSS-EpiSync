package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CodesetGroupMetadataRepository extends JpaRepository<CodesetGroupMetadata, Long>, JpaSpecificationExecutor<CodesetGroupMetadata> {

}