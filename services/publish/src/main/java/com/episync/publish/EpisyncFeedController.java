package com.episync.publish;

import com.episync.publish.entity.EpisyncMmg;
import com.episync.publish.service.EpisyncFeedService;
import com.episync.publish.shared.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public EpisyncFeedController(EpisyncFeedService feedService) {
        this.feedService = feedService;
    }

    @Operation(summary = "Get all the feed records")
    @GetMapping(path = "/all")
    public ResponseEntity<?> getEpisyncMmgAll(@RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeed());
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

    @Operation(summary = "Filter by admission date/range")
    @Parameter(name = "end", description = DESC_DATE_RANGE)
    @GetMapping("/filter/date/admission")
    public ResponseEntity<?> getFeedByAdmissionDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> end,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedByAdmissionDateRange(date, end));
    }

    @Operation(summary = "Filter by deceased date/range")
    @Parameter(name = "end", description = DESC_DATE_RANGE)
    @GetMapping("/filter/date/deceased")
    public ResponseEntity<?> getFeedByDeceasedDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> end,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedByDeceasedDateRange(date, end));
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

    @Operation(summary = "Filter by date electronically submitted to CDC")
    @Parameter(name = "end", description = DESC_DATE_RANGE)
    @GetMapping("/filter/date/submitted")
    public ResponseEntity<?> getFeedByDateReported(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> end,
            @RequestParam(required = false) boolean export) {
        return ResponseEntity.ok(feedService.getEpisyncFeedBySubmittedDateRange(date, end));
    }

}
