package com.episync.publish.service;

import com.episync.publish.entity.EpisyncMmg;
import com.episync.publish.entity.EpisyncRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EpisyncFeedServiceImpl implements EpisyncFeedService {

    private final EpisyncRepository repository;

    public EpisyncFeedServiceImpl(EpisyncRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<EpisyncMmg> getEpisyncFeed() {
        return repository.findAll();
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
    public List<EpisyncMmg> getEpisyncFeedByDeceasedDateRange(LocalDate begin, Optional<LocalDate> end) {
        return repository.findEpisyncMmgByDeceasedDateBetween(begin, end.orElse(begin));
    }

    @Override
    public List<EpisyncMmg> getEpisyncFeedBySubjectAge(Integer age, Optional<Integer> max, String unit) {
        return repository.findEpisyncMmgByAgeAtCaseInvestigationBetweenAndAgeUnitAtCaseInvestigation(age, max.orElse(age), unit);
    }

    @Override
    public List<EpisyncMmg> getEpisyncFeedBySubmittedDateRange(LocalDateTime begin, Optional<LocalDateTime> end) {
        return repository.findEpisyncMmgByDateFirstSubmittedBetween(begin, end.orElse(begin));
    }
}
