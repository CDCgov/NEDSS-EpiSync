package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaNndMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WaNndMetadataRepository extends JpaRepository<WaNndMetadata, Long>, JpaSpecificationExecutor<WaNndMetadata> {

    List<WaNndMetadata> findAllByWaTemplateUid(Long uid);
}