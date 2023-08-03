package gov.cdc.nbs.questionbank.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {
}
