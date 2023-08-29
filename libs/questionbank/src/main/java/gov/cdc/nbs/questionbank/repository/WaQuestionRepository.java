package gov.cdc.nbs.questionbank.repository;

import gov.cdc.nbs.questionbank.entity.WaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long>, JpaSpecificationExecutor<WaQuestion> {

}