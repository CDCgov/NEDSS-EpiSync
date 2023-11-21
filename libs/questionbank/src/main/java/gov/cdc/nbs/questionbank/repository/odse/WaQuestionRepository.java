package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long>, JpaSpecificationExecutor<WaQuestion> {

    List<WaQuestion> findAllByQuestionIdentifierIsIn(Collection<String> identifiers);
}