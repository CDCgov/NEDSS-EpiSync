package gov.cdc.episync.publish.mmg.service;

import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;

import gov.cdc.episync.framework.EpisyncDocumentType.Type;

import java.util.Optional;

public interface EpisyncMmgService {

    ResponseEntity<?> process(InputStreamSource xmlFile, Type type, Optional<Long> uid, String... params);

    ResponseEntity<?> extract(Long uid);
}
