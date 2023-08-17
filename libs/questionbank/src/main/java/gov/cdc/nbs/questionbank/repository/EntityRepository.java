package gov.cdc.nbs.questionbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T, ID> extends JpaRepository<T, ID> {
}
