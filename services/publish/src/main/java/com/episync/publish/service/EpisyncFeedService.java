package com.episync.publish.service;

import com.episync.publish.entity.EpisyncMmg;
import org.springframework.core.io.InputStreamSource;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EpisyncFeedService {

    List<EpisyncMmg> getEpisyncFeed(LocalDate begin, Optional<LocalDate> end);

    List<EpisyncMmg> getEpisyncFeedByBirthdayRange(LocalDate begin, Optional<LocalDate> end);

    List<EpisyncMmg> getEpisyncFeedByResidenceInfo(String country, Optional<String> state, Optional<String> zip);

    List<EpisyncMmg> getEpisyncFeedByAdmissionDateRange(LocalDate begin, Optional<LocalDate> end);

    List<EpisyncMmg> getEpisyncFeedBySubjectAge(Integer min, Optional<Integer> max, String unit);

    URI postEpisyncFeedCsv(InputStreamSource csvFeed, long size) throws IOException;
}