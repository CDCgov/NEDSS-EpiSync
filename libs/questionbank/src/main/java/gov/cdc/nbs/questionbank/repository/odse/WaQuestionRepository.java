package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long>, JpaSpecificationExecutor<WaQuestion> {

}