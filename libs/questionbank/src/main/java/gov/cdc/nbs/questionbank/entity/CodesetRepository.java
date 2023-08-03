package gov.cdc.nbs.questionbank.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodesetRepository extends JpaRepository<Codeset, CodesetId> {
}
