package com.episync.publish;

import com.episync.publish.entity.EpisyncRepository;
import com.episync.publish.entity.EpisyncMmg;
import com.episync.publish.shared.CsvWriterBean;
import com.episync.publish.shared.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import javax.servlet.http.HttpServletResponse;

@Tag(name="Episync Data Feed")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EpisyncMmg.class))}),
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))})
})
@RestController
@RequestMapping("/feed")
public class EpisyncFeedController {

    private final EpisyncRepository ddlRepository;
    private final CsvWriterBean csvWriterBean;

    public EpisyncFeedController(EpisyncRepository ddlRepository, CsvWriterBean csvWriterBean) {
        this.ddlRepository = ddlRepository;
        this.csvWriterBean = csvWriterBean;
    }

    @Operation(summary = "Get all the feed records")
    @GetMapping(path = "/all")
    public ResponseEntity<?>  getEpisyncMmgAll() {
        return ResponseEntity.ok(ddlRepository.findAll());
    }

    @Operation(summary = "Export all the feed")
    @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))})
    @GetMapping(path = "/all/export")
    public ResponseEntity<?> getEpisyncMmgExport(HttpServletResponse response) throws IOException {
        URI uri = csvWriterBean.writeCsvToS3(ddlRepository.findAll());
        return ResponseEntity.created(uri).body(new SimpleResponse(HttpStatus.CREATED.value(), "Export to S3: success"));
    }

    @Operation(summary = "Filter by patient's date of birth")
    @GetMapping("/filter/birthday/{date}")
    public ResponseEntity<?> getFeedByBirthDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByBirthDate(date));
    }

    @Operation(summary = "Filter by subject's current sex")
    @GetMapping("/filter/sex/{sex}")
    public ResponseEntity<?> getFeedBySex(@PathVariable String sex) throws IOException {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgBySex(sex));
    }

    @Operation(summary = "Filter by race category")
    @GetMapping("/filter/race/{race}")
    public ResponseEntity<?> getFeedByRace(@PathVariable String race) throws IOException {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByRace(race));
    }

    @Operation(summary = "Filter by country of birth")
    @GetMapping("/filter/birth-country/{country}")
    public ResponseEntity<?> getFeedByBirthCountry(@PathVariable String country) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByBirthCountry(country));
    }

    @Operation(summary = "Filter by country of usual residence")
    @GetMapping("/filter/residence-country/{country}")
    public ResponseEntity<?> getFeedByResidenceCountry(@PathVariable String country) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByCountryOfResidence(country));
    }

    @Operation(summary = "Filter by subject's address zip")
    @GetMapping("/filter/zip/{zip}")
    public ResponseEntity<?> getFeedBySubjectZip(@PathVariable String zip) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgBySubjectZip(zip));
    }

    @Operation(summary = "Filter by date of illness onset")
    @GetMapping("/filter/illness-start/{date}")
    public ResponseEntity<?> getFeedByDateOfIllness(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByDateOfIllness(date));
    }

    @Operation(summary = "Filter by illness end date")
    @GetMapping("/filter/illness-end/{date}")
    public ResponseEntity<?> getFeedByDateOfIllnessEndDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByIllnessEndDate(date));
    }

    @Operation(summary = "Filter by pregnancy status")
    @GetMapping("/filter/pregnant/{ynu}")
    public ResponseEntity<?> getFeedByPregnancyStatus(@PathVariable String ynu) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByPregnancyStatus(ynu));
    }

    @Operation(summary = "Filter by status of hospitalization")
    @GetMapping("/filter/hospitalized/{ynu}")
    public ResponseEntity<?> getFeedByIsHospitalized(@PathVariable String ynu) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByIsHospitalized(ynu));
    }

    @Operation(summary = "Filter by admission date")
    @GetMapping("/filter/admission-date/{date}")
    public ResponseEntity<?> getFeedByAdmissionDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByAdmissionDate(date));
    }

    @Operation(summary = "Filter by diagnosis date")
    @GetMapping("/filter/diagnosis-date/{date}")
    public ResponseEntity<?> getFeedByDiagnosisDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByDiagnosisDate(date));
    }

    @Operation(summary = "Filter by status of death")
    @GetMapping("/filter/died/{ynu}")
    public ResponseEntity<?> getFeedBySubjectDied(@PathVariable String ynu) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgBySubjectDied(ynu));
    }

    @Operation(summary = "Filter by deceased date")
    @GetMapping("/filter/deceased-date/{date}")
    public ResponseEntity<?> getFeedByDeceasedDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByDeceasedDate(date));
    }

    @Operation(summary = "Filter by age at case date")
    @GetMapping("/filter/age/{age}/{unit}")
    public ResponseEntity<?> getFeedByAgeAtCaseInvestigation(@PathVariable String age,
                                                             @PathVariable String unit) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByAgeAtCaseInvestigationAndAgeUnitAtCaseInvestigation(age, unit));
    }

    @Operation(summary = "Filter by date reported to health department")
    @GetMapping("/filter/date-reported/{date}")
    public ResponseEntity<?> getFeedByDateReported(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ddlRepository.findEpisyncMmgByDateReported(date));
    }

}
