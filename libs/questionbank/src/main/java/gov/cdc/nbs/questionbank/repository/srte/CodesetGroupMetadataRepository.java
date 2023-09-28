package gov.cdc.nbs.questionbank.repository.srte;

import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface CodesetGroupMetadataRepository extends JpaRepository<CodesetGroupMetadata, Long>, JpaSpecificationExecutor<CodesetGroupMetadata> {

    @Query ("SELECT g FROM CodesetGroupMetadata g JOIN Codeset c ON g.codeSetNm=c.codeSetNm AND c.codeSetNm IN :codes")
    List<CodesetGroupMetadata> findAllByVadsValueSetCode(Collection<String> codes);

    @Query("SELECT COALESCE(MAX(codeSetGroupId), 0) FROM CodesetGroupMetadata")
    long getMaxCodesetGroupId();
}