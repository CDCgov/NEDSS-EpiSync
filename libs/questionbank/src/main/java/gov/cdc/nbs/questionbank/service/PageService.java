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

@Service
@RequiredArgsConstructor
@Transactional("odseTransactionManager")
public class PageService {
    private final WaTemplateRepository repository;
    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiRepository;

    public List<WaQuestion> findByIdentifiers(Collection<String> identifiers) {
        return questionRepository.findByIdentifiers(identifiers);
    }

    public WaTemplate save(WaTemplate template) {
        return repository.save(template);
    }

    public List<WaQuestion> save(Collection<WaQuestion> questions, Collection<WaUiMetadata> uiMetadata) {
        uiRepository.saveAll(uiMetadata);
        return questionRepository.saveAll(questions);
    }
}
