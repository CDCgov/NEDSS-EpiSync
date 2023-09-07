package gov.cdc.episync.publish.mmg.service;

import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;

import gov.cdc.episync.framework.EpisyncDocumentType.Type;

public interface EpisyncMmgService {

    ResponseEntity<?> process(InputStreamSource xmlFile, Type type, String... params);
}
