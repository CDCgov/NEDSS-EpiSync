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

    List<EpisyncMmg> findEpisyncMmgByBirthDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgBySex(String sex);
    List<EpisyncMmg> findEpisyncMmgByRace(String race);


    List<EpisyncMmg> findEpisyncMmgByBirthCountry(String country);
    List<EpisyncMmg> findEpisyncMmgByCountryOfResidence(String country);
    List<EpisyncMmg> findEpisyncMmgBySubjectZip(String zip);


    List<EpisyncMmg> findEpisyncMmgByDateOfIllness(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByIllnessEndDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByIllnessDuration(String duration);


    List<EpisyncMmg> findEpisyncMmgByPregnancyStatus(String status);
    List<EpisyncMmg> findEpisyncMmgByDiagnosisDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByIsHospitalized(String hospitalized);
    List<EpisyncMmg> findEpisyncMmgByAdmissionDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByDischargeDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByHospitalStayInDays(Integer duration);


    List<EpisyncMmg> findEpisyncMmgBySubjectDied(String ynu);
    List<EpisyncMmg> findEpisyncMmgByDeceasedDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByConditionCode(String code);

    List<EpisyncMmg> findEpisyncMmgByAgeAtCaseInvestigationAndAgeUnitAtCaseInvestigation(String age, String unit);

    List<EpisyncMmg> findEpisyncMmgByCaseDiseaseImportedCode(String code);
    List<EpisyncMmg> findEpisyncMmgByImportedCountry(String country);
    List<EpisyncMmg> findEpisyncMmgByImportedState(String state);
    List<EpisyncMmg> findEpisyncMmgByImportedCity(String city);
    List<EpisyncMmg> findEpisyncMmgByImportedCounty(String county);

    List<EpisyncMmg> findEpisyncMmgByCountryOfExposure(String country);
    List<EpisyncMmg> findEpisyncMmgByStateOfExposure(String state);
    List<EpisyncMmg> findEpisyncMmgByCityOfExposure(String ciy);
    List<EpisyncMmg> findEpisyncMmgByCountyOfExposure(String county);

    List<EpisyncMmg> findEpisyncMmgByTransmissionMode(String mode);
    List<EpisyncMmg> findEpisyncMmgByCaseStatusCode(String code);
    List<EpisyncMmg> findEpisyncMmgByImmediateNotifiableCondition(String condition);
    List<EpisyncMmg> findEpisyncMmgByCaseOutbreakIndicator(String indicator);
    List<EpisyncMmg> findEpisyncMmgByCaseOutbreakName(String name);
    List<EpisyncMmg> findEpisyncMmgByNotificationResultStatus(String status);
    List<EpisyncMmg> findEpisyncMmgByJurisdictionCode(String code);

    List<EpisyncMmg> findEpisyncMmgByCaseInvestigationStartDate(LocalDate date);
    List<EpisyncMmg> findEpisyncMmgByDateFirstSubmitted(LocalDateTime date);
    List<EpisyncMmg> findEpisyncMmgByDateReported(LocalDate date);




}
