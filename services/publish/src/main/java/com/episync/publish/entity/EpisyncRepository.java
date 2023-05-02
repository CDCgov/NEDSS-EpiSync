package com.episync.publish.entity;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Hidden
@RepositoryRestResource
public interface EpisyncRepository extends JpaRepository<EpisyncMmg, EpisyncMmgId> {

    List<EpisyncMmg> findEpisyncMmgByBirthDateBetween(LocalDate start, LocalDate end);

    List<EpisyncMmg> findEpisyncMmgByCountryOfResidence(String country);
    List<EpisyncMmg> findEpisyncMmgByCountryOfResidenceAndSubjectState(String country, String state);
    List<EpisyncMmg> findEpisyncMmgByCountryOfResidenceAndSubjectZip(String country, String zip);
    List<EpisyncMmg> findEpisyncMmgByCountryOfResidenceAndSubjectStateAndSubjectZip(String country, String state, String zip);

    List<EpisyncMmg> findEpisyncMmgByAdmissionDateBetween(LocalDate begin, LocalDate end);

    List<EpisyncMmg> findEpisyncMmgByDeceasedDateBetween(LocalDate begin, LocalDate end);
    List<EpisyncMmg> findEpisyncMmgByAgeAtCaseInvestigationBetweenAndAgeUnitAtCaseInvestigation(Integer min, Integer max, String unit);
    List<EpisyncMmg> findEpisyncMmgByDateFirstSubmittedBetween(LocalDateTime begin, LocalDateTime end);




}
