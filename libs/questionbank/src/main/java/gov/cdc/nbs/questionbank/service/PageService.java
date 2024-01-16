package gov.cdc.nbs.questionbank.service;

import gov.cdc.nbs.questionbank.entity.odse.*;
import gov.cdc.nbs.questionbank.repository.odse.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional("odseTransactionManager")
public class PageService {
    private final WaTemplateRepository repository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;
    private final WaNndMetadataRepository nndRepository;
    private final WaRuleMetadataRepository ruleRepository;
    private final WaRdbMetadataRepository rdbRepository;

    public Optional<WaTemplate> getTemplate(Long uid) {
        return repository.findById(uid);
    }

    public List<WaQuestion> findByIdentifiers(Collection<String> identifiers) {
        return questionRepository.findAllByQuestionIdentifierIsIn(identifiers);
    }

    public List<WaUiMetadata> findUiByTemplateUid(Long uid) {
        return uiRepository.findAllByWaTemplateUidOrderByOrderNbr(uid);
    }

    public List<WaRuleMetadata> findRulesByTemplateUid(Long uid) {
        return ruleRepository.findAllByWaTemplateUid(uid);
    }

    public List<WaQuestion> save(Collection<WaQuestion> questions) {
        return questionRepository.saveAll(questions);
    }

    public WaTemplate save(WaTemplate template,
                           Collection<WaUiMetadata> uiMetadata,
                           Collection<WaNndMetadata> nndMetadata,
                           Collection<WaRuleMetadata> ruleMetadata,
                           Collection<WaRdbMetadata> rdbMetadata) {
        WaTemplate saved = repository.save(template);
        uiMetadata.forEach(ui -> ui.setWaTemplateUid(saved.getWaTemplateUid()));
        List<WaUiMetadata> uiData = uiRepository.saveAll(uiMetadata);

        ruleMetadata.forEach(rule -> rule.setWaTemplateUid(saved.getWaTemplateUid()));
        ruleRepository.saveAll(ruleMetadata);

        Map<String, WaUiMetadata> uiMap = uiData.stream().collect(Collectors.toMap(WaUiMetadata::getQuestionIdentifier, Function.identity(), (v1, v2) -> v1));
        nndMetadata.forEach(nnd -> {
            WaUiMetadata ui = uiMap.get(nnd.getQuestionIdentifier());
            nnd.setWaTemplateUid(ui.getWaTemplateUid());
            nnd.setWaUiMetadataUid(ui.getWaUiMetadataUid());
        });
        nndRepository.saveAll(nndMetadata);

        rdbMetadata.forEach(rdb -> {
            WaUiMetadata ui = uiMap.get(rdb.getQuestionIdentifier());
            rdb.setWaTemplateUid(ui.getWaTemplateUid());
            rdb.setWaUiMetadataUid(ui.getWaUiMetadataUid());
        });
        rdbRepository.saveAll(rdbMetadata);

        return saved;
    }

    public List<WaUiMetadata> copyWaTemplateUIMetaData(Long uid, Long userId) {
        List<WaUiMetadata> uiData = findUiByTemplateUid(uid);
        List<WaUiMetadata> clonedUiData = new ArrayList<>();
        for (WaUiMetadata original : uiData) {
            WaUiMetadata clone = WaUiMetadata.clone(original);
            clone.setAddUserId(userId);
            clone.setAddTime(Instant.now());
            clone.setLastChgUserId(userId);
            clone.setLastChgTime(Instant.now());

            clonedUiData.add(clone);
        }
        return clonedUiData;
    }

    public List<WaNndMetadata> copyWaTemplateNndMetaData(Long uid, Long userId) {
        List<WaNndMetadata> nndData = nndRepository.findAllByWaTemplateUid(uid);
        List<WaNndMetadata> clonedUiData = new ArrayList<>();
        for (WaNndMetadata original : nndData) {
            WaNndMetadata clone = WaNndMetadata.clone(original);
            clone.setAddUserId(userId);
            clone.setAddTime(Instant.now());
            clone.setLastChgUserId(userId);
            clone.setLastChgTime(Instant.now());

            clonedUiData.add(clone);
        }
        return clonedUiData;
    }

    public List<WaRuleMetadata> copyWaTemplateRuleMetaData(Long uid, Long userId) {
        List<WaRuleMetadata> ruleData = findRulesByTemplateUid(uid);
        List<WaRuleMetadata> clonedRuleData = new ArrayList<>();
        for (WaRuleMetadata original : ruleData) {
            WaRuleMetadata clone = WaRuleMetadata.clone(original);
            clone.setAddUserId(userId);
            clone.setAddTime(Instant.now());
            clone.setLastChgUserId(userId);
            clone.setLastChgTime(Instant.now());

            clonedRuleData.add(clone);
        }
        return clonedRuleData;
    }

    public List<WaRdbMetadata> copyWaTemplateRdbMetaData(Long uid, Long userId) {
        List<WaRdbMetadata> rdbData = rdbRepository.findAllByWaTemplateUid(uid);
        List<WaRdbMetadata> clonedRdbData = new ArrayList<>();
        for (WaRdbMetadata original : rdbData) {
            WaRdbMetadata clone = WaRdbMetadata.clone(original);
            clone.setAddUserId(userId);
            clone.setAddTime(Instant.now());
            clone.setLastChgUserId(userId);
            clone.setLastChgTime(Instant.now());

            clonedRdbData.add(clone);
        }
        return clonedRdbData;
    }
}
