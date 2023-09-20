package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long>, JpaSpecificationExecutor<WaQuestion> {

    @Query("SELECT q FROM WaQuestion q WHERE q.questionIdentifier IN :identifiers")
    List<WaQuestion> findByIdentifiers(Collection<String> identifiers);
}