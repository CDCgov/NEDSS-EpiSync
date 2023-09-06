package gov.cdc.nbs.questionbank.repository.odse;

import gov.cdc.nbs.questionbank.entity.odse.WaTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WaTemplateRepository extends JpaRepository<WaTemplate, Long>, JpaSpecificationExecutor<WaTemplate> {

}