package com.episync.publish.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "episync_mmg")
@IdClass(EpisyncMmgId.class)
@Getter @Setter @NoArgsConstructor
public class EpisyncMmg {

    @Id
    @Column(name = "episync_mmg_message_profile_identifier")
    @Schema(description = "Message Profile Identifier")
    private String messageProfileIdentifier;

    @Id
    @Column(name = "episync_mmg_local_subject_id")
    @Schema(description = "Local Subject ID")
    private String localSubjectId;

    @Column(name = "episync_mmg_birth_date")
    @Schema(description = "Patient’s date of birth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "episync_mmg_sex")
    @Schema(description = "Subject’s current sex")
    private String sex;

    @Column(name = "episync_mmg_race")
    @Schema(description = "Race category - Major OMB Race Categories")
    private String race;

    @Column(name = "episync_mmg_other_race")
    @Schema(description = "Other Race Text")
    private String otherRace;

    @Column(name = "episync_mmg_ethnic_group")
    @Schema(description = "Based on the self-identity of the subject as Hispanic or Latino")
    private String ethnicGroup;

    @Column(name = "episync_mmg_birth_country")
    @Schema(description = "Country of Birth")
    private String birthCountry;

    @Column(name = "episync_mmg_other_birth_place")
    @Schema(description = "Other Birth Place")
    private String birthPlace;

    @Column(name = "episync_mmg_country_of_residence")
    @Schema(description = "Country of usual residence")
    private String countryOfResidence;

    @Column(name = "episync_mmg_subject_address_county")
    @Schema(description = "County of residence of the subject")
    private String subjectCounty;

    @Column(name = "episync_mmg_subject_address_state")
    @Schema(description = "State of residence of the subject")
    private String subjectState;

    @Column(name = "episync_mmg_subject_address_zip")
    @Schema(description = "ZIP Code of residence of the subject")
    private String subjectZip;

    @Column(name = "episync_mmg_date_of_illness_onset")
    @Schema(description = "Date of the beginning of the illness")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateOfIllness;

    @Column(name = "episync_mmg_illness_end_date")
    @Schema(description = "Date at which the disease or condition ends")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate illnessEndDate;

    @Column(name = "episync_mmg_illness_duration")
    @Schema(description = "Length of time this subject had this disease or condition")
    private String illnessDuration;

    @Column(name = "episync_mmg_illness_duration_units")
    @Schema(description = "Unit of time used to describe the length of the illness or condition")
    private String illnessDurationUnits;

    @Column(name = "episync_mmg_pregnancy_status")
    @Schema(description = "Indicates whether the subject was pregnant at the time of the event")
    private String pregnancyStatus;

    @Column(name = "episync_mmg_diagnosis_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "Earliest date of diagnosis being reported to public health system.")
    private LocalDate diagnosisDate;

    @Column(name = "episync_mmg_hospitalized")
    @Schema(description = "Indicates whether the subject was hospitalized because of this event?")
    private String isHospitalized;

    @Column(name = "episync_mmg_admission_date")
    @Schema(description = "Subject’s most recent admission date to the hospital for the condition covered by the investigation")
    private LocalDate admissionDate ;

    @Column(name = "episync_mmg_discharge_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "Subject's most recent discharge date from the hospital for the condition covered by the investigation")
    private LocalDate dischargeDate;

    @Column(name = "episync_mmg_duration_of_hospital_stay_in_days")
    @Schema(description = "Subject's duration of stay at the hospital for the condition covered by the investigation")
    private Integer hospitalStayInDays;

    @Column(name = "episync_mmg_subject_died")
    @Schema(description = "Indicates whether the subject die from this illness or complications of this illness")
    private String subjectDied;

    @Column(name = "episync_mmg_deceased_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(description = "Indicates the date of death.")
    private LocalDate deceasedDate;

    @Column(name = "episync_mmg_condition_code")
    @Schema(description = "Condition or event that constitutes the reason the notification is being sent")
    private String conditionCode;

    @Column(name = "episync_mmg_local_record_id")
    @Schema(description = "Sending system-assigned local ID of the case investigation with which the subject is associated")
    private String localRecordId;

    @Column(name = "episync_mmg_state_case_identifier")
    @Schema(description = "States use this identifier to link NEDSS investigations back to their own state investigations")
    private String stateCaseId;

    @Column(name = "episync_mmg_legacy_case_identifier")
    @Schema(description = "Links current case notifications to case notifications submitted by a previous system")
    private String legacyCaseId;

    @Column(name = "episync_mmg_age_at_case_investigation")
    @Schema(description = "Subject age at time of case investigation")
    private Integer ageAtCaseInvestigation;

    @Column(name = "episync_mmg_age_unit_at_case_investigation")
    @Schema(description = "Subject age unit at time of case investigation")
    private String ageUnitAtCaseInvestigation;

    @Column(name = "episync_mmg_case_disease_imported_code")
    @Schema(description = "Indication of where the disease/condition was likely acquired")
    private String caseDiseaseImportedCode;

    @Column(name = "episync_mmg_imported_country")
    @Schema(description = "If the disease or condition was imported, indicates the country in which the disease was likely acquired")
    private String importedCountry;

    @Column(name = "episync_mmg_imported_state")
    @Schema(description = "If the disease or condition was imported, indicates the state in which the disease was likely acquired")
    private String importedState;

    @Column(name = "episync_mmg_imported_city")
    @Schema(description = "If the disease or condition was imported, indicates the city in which the disease was likely acquired")
    private String importedCity;

    @Column(name = "episync_mmg_imported_county")
    @Schema(description = "If the disease or condition was imported, contains the county of origin of the disease or condition")
    private String importedCounty;

    @Column(name = "episync_mmg_country_of_exposure")
    @Schema(description = "Indicates the country in which the disease was likely acquired")
    private String countryOfExposure;

    @Column(name = "episync_mmg_state_or_province_of_exposure")
    @Schema(description = "Indicates the state (or Province) in which the disease was likely acquired")
    private String stateOfExposure;

    @Column(name = "episync_mmg_city_of_exposure")
    @Schema(description = "Indicates the city in which the disease was likely acquired")
    private String cityOfExposure;

    @Column(name = "episync_mmg_county_of_exposure")
    @Schema(description = "Indicates the county in which the disease was likely acquired (US only)")
    private String countyOfExposure;

    @Column(name = "episync_mmg_transmission_mode")
    @Schema(description = "Code for the mechanism by which disease or condition was acquired by the subject of the investigation")
    private String transmissionMode;

    @Column(name = "episync_mmg_case_class_status_code")
    @Schema(description = "Status of the case/event CSTE/CDC/ surveillance case definitions")
    private String caseStatusCode;

    @Column(name = "episync_mmg_immediate_national_notifiable_condition")
    @Schema(description = "Indicates whether the case meets the criteria for immediate notification to CDC")
    private String immediateNotifiableCondition;

    @Column(name = "episync_mmg_case_outbreak_indicator")
    @Schema(description = "Denotes whether the reported case was associated with an identified outbreak")
    private String caseOutbreakIndicator;

    @Column(name = "episync_mmg_case_outbreak_name")
    @Schema(description = "A state-assigned name for an identified outbreak")
    private String caseOutbreakName;

    @Column(name = "episync_mmg_notification_result_status")
    @Schema(description = "Status of the notification")
    private String notificationResultStatus;

    @Column(name = "episync_mmg_jurisdiction_code")
    @Schema(description = "Identifier for the physical site from which the notification is being submitted")
    private String jurisdictionCode;

    @Column(name = "episync_mmg_reporting_source_type_code")
    @Schema(description = "Type of facility or provider associated with the source of information sent to Public Health")
    private String reportingSourceTypeCode;

    @Column(name = "episync_mmg_reporting_source_zip_code")
    @Schema(description = "ZIP Code of the reporting source for this case")
    private String reportingSourceZipCode;

    @Column(name = "episync_mmg_binational_reporting_criteria")
    @Schema(description = "For cases meeting the binational criteria, select all the criteria which are me")
    private String binationalReportingCriteria;

    @Column(name = "episync_mmg_person_reporting_to_cdc_name")
    @Schema(description = "Name of the person who is reporting the case to the CDC")
    private String personReportingName;

    @Column(name = "episync_mmg_person_reporting_to_cdc_phone")
    @Schema(description = "Phone Number of the person who is reporting the case to the CDC")
    private String personReportingPhone;

    @Column(name = "episync_mmg_person_reporting_to_cdc_email")
    @Schema(description = "Email Address of the person reporting the case to the CDC")
    private String personReportingEmail;

    @Column(name = "episync_mmg_case_investigation_start_date")
    @Schema(description = "The date the case investigation was initiated")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate caseInvestigationStartDate;

    @Column(name = "episync_mmg_date_first_electronically_submitted")
    @Schema(description = "Date/time the notification was first electronically sent to CDC")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFirstSubmitted;

    @Column(name = "episync_mmg_date_of_electronic_case_notification_to_cdc")
    @Schema(description = "Date/time this version of the electronic case notification was sent")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCaseNotificationToCdc;

    @Column(name = "episync_mmg_date_reported")
    @Schema(description = "Date that a health department first suspected the subject might have the condition")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateReported;

    @Column(name = "episync_mmg_earliest_date_reported_to_county")
    @Schema(description = "Earliest date reported to county public health system")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate earliestDateReportedCounty;

    @Column(name = "episync_mmg_earliest_date_reported_to_state")
    @Schema(description = "Earliest date reported to state public health system")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate earliestDateReportedState;

    @Column(name = "episync_mmg_mmwr_week")
    @Schema(description = "MMWR Week for which case information is to be counted for MMWR publication")
    private String mmwrWeek;

    @Column(name = "episync_mmg_mmwr_year")
    @Schema(description = "MMWR Year (YYYY) for which case information is to be counted for MMWR publication")
    private String mmwrYear;

    @Column(name = "episync_mmg_date_cdc_was_first_verbally_notified_of_this_case")
    @Schema(description = "Date the case was first verbally reported to the CDC")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateCdcVerballyNotified;

    @Column(name = "episync_mmg_date_first_reported_to_phd")
    @Schema(description = "Date the report was first sent to the public health department")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dateFirstReportingPhd;

    @Column(name = "episync_mmg_reporting_state")
    @Schema(description = "State reporting the notification")
    private String reportingState;

    @Column(name = "episync_mmg_reporting_county")
    @Schema(description = "County reporting the notification")
    private String reportingCounty;

    @Column(name = "episync_mmg_national_reporting_jurisdiction")
    @Schema(description = "National jurisdiction reporting the notification to CDC")
    private String nationalReportingJurisdiction;

    @Column(name = "episync_mmg_comment")
    @Schema(description = "To communicate anything unusual about the case")
    private String mmwrComment;

}
