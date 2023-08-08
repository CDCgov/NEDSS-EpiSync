package gov.cdc.episync.publish.service;

import gov.cdc.episync.publish.entity.EpisyncMmg;
import gov.cdc.episync.publish.entity.EpisyncRepository;
import gov.cdc.episync.publish.shared.CsvWriterS3Bean;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EpisyncFeedServiceImpl implements EpisyncFeedService {

    private final EpisyncRepository repository;
    private final CsvWriterS3Bean csvWriter;

    @Override
    public List<EpisyncMmg> getEpisyncFeed(LocalDate begin, Optional<LocalDate> end) {
        return repository.findEpisyncMmgByDateReportedBetween(begin, end.orElse(begin));
    }

    @Override
    public List<EpisyncMmg> getEpisyncFeedByBirthdayRange(LocalDate begin, Optional<LocalDate> end) {
        return repository.findEpisyncMmgByBirthDateBetween(begin, end.orElse(begin));
    }

    @Override
    public List<EpisyncMmg> getEpisyncFeedByResidenceInfo(String country, Optional<String> state, Optional<String> zip) {
        if (state.isPresent()) {
            if (zip.isPresent()) {
                return repository.findEpisyncMmgByCountryOfResidenceAndSubjectStateAndSubjectZip(country, state.get(), zip.get());
            } else {
                return repository.findEpisyncMmgByCountryOfResidenceAndSubjectState(country, state.get());
            }
        } else if (zip.isPresent()) {
            return repository.findEpisyncMmgByCountryOfResidenceAndSubjectZip(country, zip.get());
        } else {
            return repository.findEpisyncMmgByCountryOfResidence(country);
        }
    }

    @Override
    public List<EpisyncMmg> getEpisyncFeedByAdmissionDateRange(LocalDate begin, Optional<LocalDate> end) {
        return repository.findEpisyncMmgByAdmissionDateBetween(begin, end.orElse(begin));
    }
    @Override
    public List<EpisyncMmg> getEpisyncFeedBySubjectAge(Integer age, Optional<Integer> max, String unit) {
        return repository.findEpisyncMmgByAgeAtCaseInvestigationBetweenAndAgeUnitAtCaseInvestigation(age, max.orElse(age), unit);
    }

    @Override
    public URI postEpisyncFeedCsv(InputStreamSource csvFeed, long size) throws IOException {
        return csvWriter.writeStreamToS3(csvFeed.getInputStream(), size);
    }
}
