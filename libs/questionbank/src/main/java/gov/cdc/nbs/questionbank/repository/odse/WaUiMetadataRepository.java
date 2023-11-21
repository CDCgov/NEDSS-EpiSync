package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WaUiMetadataRepository extends JpaRepository<WaUiMetadata, Long>, JpaSpecificationExecutor<WaUiMetadata> {

    List<WaUiMetadata> findAllByWaTemplateUidOrderByOrderNbr(Long uid);
}