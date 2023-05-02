package com.episync.publish.service;

import com.episync.publish.entity.EpisyncMmg;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EpisyncFeedService {

    List<EpisyncMmg> getEpisyncFeed();

    List<EpisyncMmg> getEpisyncFeedByBirthdayRange(LocalDate begin, Optional<LocalDate> end);

    List<EpisyncMmg> getEpisyncFeedByResidenceInfo(String country, Optional<String> state, Optional<String> zip);

    List<EpisyncMmg> getEpisyncFeedByAdmissionDateRange(LocalDate begin, Optional<LocalDate> end);
    List<EpisyncMmg> getEpisyncFeedByDeceasedDateRange(LocalDate begin, Optional<LocalDate> end);

    List<EpisyncMmg> getEpisyncFeedBySubjectAge(Integer min, Optional<Integer> max, String unit);

    List<EpisyncMmg> getEpisyncFeedBySubmittedDateRange(LocalDateTime begin, Optional<LocalDateTime> end);
}
