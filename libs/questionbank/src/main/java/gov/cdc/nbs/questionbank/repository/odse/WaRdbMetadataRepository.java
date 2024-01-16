package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaRdbMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WaRdbMetadataRepository extends JpaRepository<WaRdbMetadata, Long>, JpaSpecificationExecutor<WaRdbMetadata> {

    List<WaRdbMetadata> findAllByWaTemplateUid(Long uid);
}