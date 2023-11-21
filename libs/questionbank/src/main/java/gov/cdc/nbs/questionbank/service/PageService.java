package gov.cdc.nbs.questionbank.service;

import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import gov.cdc.nbs.questionbank.entity.odse.WaUiMetadata;
import gov.cdc.nbs.questionbank.repository.odse.WaQuestionRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaTemplateRepository;
import gov.cdc.nbs.questionbank.repository.odse.WaUiMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional("odseTransactionManager")
public class PageService {
    private final WaTemplateRepository repository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;

    public Optional<WaTemplate> getTemplate(Long uid) {
        return repository.findById(uid);
    }

    public List<WaQuestion> findByIdentifiers(Collection<String> identifiers) {
        return questionRepository.findAllByQuestionIdentifierIsIn(identifiers);
    }

    public List<WaQuestion> save(Collection<WaQuestion> questions) {
        return questionRepository.saveAll(questions);
    }

    public WaTemplate save(WaTemplate template, Collection<WaUiMetadata> uiMetadata) {
        WaTemplate saved = repository.save(template);
        uiMetadata.forEach(ui -> ui.setWaTemplateUid(saved.getWaTemplateUid()));
        uiRepository.saveAll(uiMetadata);
        return saved;
    }

    public List<WaUiMetadata> findUiByTemplateUid(Long uid) {
        return uiRepository.findAllByWaTemplateUidOrderByOrderNbr(uid);
    }
}
