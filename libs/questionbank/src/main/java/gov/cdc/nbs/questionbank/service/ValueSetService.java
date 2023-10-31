package gov.cdc.nbs.questionbank.service;

import gov.cdc.nbs.questionbank.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.srte.Codeset;
import gov.cdc.nbs.questionbank.entity.srte.CodesetGroupMetadata;
import gov.cdc.nbs.questionbank.repository.srte.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.repository.srte.CodesetGroupMetadataRepository;
import gov.cdc.nbs.questionbank.repository.srte.CodesetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
@Transactional("srteTransactionManager")
public class ValueSetService {
    private final CodesetGroupMetadataRepository groupRepository;
    private final CodesetRepository codesetRepository;
    private final CodeValueGeneralRepository valueRepository;

    public List<CodesetGroupMetadata> findValueSetGroupByCode(List<String> codes) {
        return groupRepository.findAllByVadsValueSetCode(codes);
    }

    public long getMaxGroupId() {
        return groupRepository.getMaxCodesetGroupId();
    }

    public CodesetGroupMetadata save(Codeset cset, List<CodeValueGeneral> values) {
        CodesetGroupMetadata saved = groupRepository.save(cset.getCodeSetGroup());
        codesetRepository.save(cset);
        valueRepository.saveAll(values);

        return saved;
    }
}
