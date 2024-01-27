package gov.cdc.nbs.questionbank.service;

import gov.cdc.nbs.questionbank.entity.srte.*;
import gov.cdc.nbs.questionbank.repository.srte.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service @RequiredArgsConstructor
@Transactional("srteTransactionManager")
public class ValueSetService {
    private final CodesetGroupMetadataRepository groupRepository;
    private final CodesetRepository codesetRepository;
    private final CodeValueGeneralRepository valueRepository;
    private final JurisdictionCodeRepository jurisdictionCodeRepository;
    private final ProgramAreaCodeRepository programAreaCodeRepository;
    private final ConditionCodeRepository conditionCodeRepository;

    public List<CodesetGroupMetadata> findValueSetGroupByCodes(Collection<String> codes) {
        return codes.isEmpty() ? Collections.emptyList() : groupRepository.findAllByCodeSetNmIsIn(codes);
    }

    public List<CodesetGroupMetadata> findValueSetGroupByGroupIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyList() : groupRepository.findAllByCodeSetGroupIdIsIn(ids);
    }

    public List<Codeset> findCodesetsByGroupIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyList() : codesetRepository.findAllByCodeSetGroupCodeSetGroupIdIsIn(ids);
    }

    public List<CodeValueGeneral> findConceptsByCodes(Collection<String> codes) {
        return codes.isEmpty() ? Collections.emptyList() : valueRepository.findAllByCodeSetNmIsIn(codes);
    }

    public List<JurisdictionCode> getJurisdictionCodes(String name) {
        return jurisdictionCodeRepository.findAllByCodeSetNm(name);
    }

    public List<ProgramAreaCode> getProgramAreaCodes(String name) {
        return programAreaCodeRepository.findAllByCodeSetNm(name);
    }

    public List<ConditionCode> getConditionCodes(String name) {
        return conditionCodeRepository.findAllByConditionCodesetNm(name);
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
