package gov.cdc.episync.publish;

import gov.cdc.episync.publish.entity.EpisyncMmg;
import gov.cdc.episync.publish.service.EpisyncExternalAPIService;
import gov.cdc.episync.publish.service.EpisyncFeedService;
import gov.cdc.episync.publish.service.QueryExecutor;
import gov.cdc.episync.publish.shared.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Tag(name="Episync Data Feed")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EpisyncMmg.class))}),
        @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))})
})
@RestController
@RequestMapping("/feed")
public class EpisyncFeedController {

    private final static String DESC_DATE_RANGE = "If present then date range applied to search";
    private final EpisyncFeedService feedService;
    private final EpisyncExternalAPIService apiService;
    private final QueryExecutor queryExecutor;

    public EpisyncFeedController(EpisyncFeedService feedService,
                                 EpisyncExternalAPIService apiService,
                                 QueryExecutor queryExecutor) {
        this.feedService = feedService;
        this.apiService = apiService;
        this. queryExecutor = queryExecutor;
    }

    @Operation(summary = "Get all the feed records for reported date/range")
    @Parameter(name = "end", description = DESC_DATE_RANGE)
    @GetMapping(path = "/all")
    public ResponseEntity<?> getEpisyncMmgAll(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> end,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeed(date, end));
    }

    @Operation(summary = "Filter by birthday/range")
    @Parameter(name = "end", description = DESC_DATE_RANGE)
    @GetMapping("/filter/date/birthday")
    public ResponseEntity<?> getFeedByBirthdayRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> end,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedByBirthdayRange(start, end));
    }

    @Operation(summary = "Filter by subject residence info")
    @GetMapping("/filter/residence")
    public ResponseEntity<?> getFeedByResidenceInfo(
            @RequestParam String country,
            @RequestParam Optional<String> state,
            @RequestParam Optional<String> zip,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedByResidenceInfo(country, state, zip));
    }

    @Operation(summary = "Filter by age at case date")
    @GetMapping("/filter/age")
    public ResponseEntity<?> getFeedByAgeAtCaseInvestigation(
            @RequestParam Integer age,
            @RequestParam Optional<Integer> maxAge,
            @RequestParam(required = false, defaultValue = "a") String unit,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedBySubjectAge(age, maxAge, unit));
    }

    @Operation(summary = "Execute a profiled query stored in config")
    @GetMapping("/query/{profile}/{query}")
    public ResponseEntity<?> getFeedByProfiledQuery(
            @PathVariable String profile,
            @PathVariable String query,
            @RequestParam Optional<String> params
            ) {
        return ResponseEntity.ok(queryExecutor.execute(profile, query, params));
    }

    @Operation(summary = "Upload CSV feed, validate and export to S3")
    @PostMapping(value = "/upload/csv", consumes = "multipart/form-data")
    public ResponseEntity<?> postFeedFromCsvFile (@RequestParam("file") MultipartFile file) {
        ResponseEntity<String> validateResponse = apiService.validateFeed(file);
        HttpStatus status = validateResponse.getStatusCode();
        if (status != HttpStatus.OK) {
            return ResponseEntity.status(status).body(SimpleResponse.of(status.value(), validateResponse.getBody()));
        }
        try {
            return ResponseEntity.created(feedService.postEpisyncFeedCsv(file, file.getSize())).body(SimpleResponse.of(HttpStatus.CREATED.value(), "Export to S3: success"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(SimpleResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Export to S3 failed: " + e.getMessage()));
        }
    }
}
