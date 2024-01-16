package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CodesetGroupMetadataRepository extends JpaRepository<CodesetGroupMetadata, Long>, JpaSpecificationExecutor<CodesetGroupMetadata> {

    List<CodesetGroupMetadata> findAllByCodeSetNmIsIn(Collection<String> codes);
    List<CodesetGroupMetadata> findAllByCodeSetGroupIdIsIn(Collection<Long> ids);

    @Query("SELECT COALESCE(MAX(codeSetGroupId), 0) FROM CodesetGroupMetadata")
    long getMaxCodesetGroupId();
}