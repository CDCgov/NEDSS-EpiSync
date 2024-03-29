package gov.cdc.episync.publish.mmg.service;

import gov.cdc.episync.framework.Episync;
import gov.cdc.episync.framework.EpisyncDocument;
import gov.cdc.episync.framework.EpisyncDocumentType.Type;
import gov.cdc.episync.framework.EpisyncExtractResult;
import gov.cdc.episync.framework.EpisyncExtractResult.ExtractResultCode;
import gov.cdc.episync.framework.EpisyncPublishResult;
import gov.cdc.episync.framework.EpisyncPublishResult.PublishResultCode;
import gov.cdc.episync.mmg.MmgDocument;
import gov.cdc.episync.nnd.NndDocument;
import gov.cdc.episync.publish.shared.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class EpisyncMmgServiceImpl implements EpisyncMmgService {
    private final Episync episync;

    @Override
    public ResponseEntity<?> process(InputStreamSource file, Type type, Optional<Long> uid, String... params) {
        try {
            EpisyncDocument document = getInstance(file, type);
            document.setUid(uid.orElse(null));
            document.setOrigin(new URL(params[0]));

            EpisyncPublishResult result = episync.publishDocument(document);
            String message = result.getResultMessage().isEmpty() ?
                    result.getResultCode().getDescription() : result.getResultMessage();

            return result.getResultCode().equals(PublishResultCode.SUCCESS) ?
                    ResponseEntity.ok(SimpleResponse.of(result.getResultCode(), message)) :
                    ResponseEntity.unprocessableEntity().body(SimpleResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Publishing failed: " + message));
        } catch (Exception e) {
            return ResponseEntity.unprocessableEntity().body(SimpleResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Publishing failed: " + e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> extract(Long uid) {
        try {
            EpisyncExtractResult result = episync.extract(uid);
            String message = result.getResultMessage().isEmpty() ?
                    result.getResultCode().getDescription() : result.getResultMessage();
            return result.getResultCode().equals(ExtractResultCode.SUCCESS) ?
                    ResponseEntity.created(result.getLocation()).body(SimpleResponse.of(result.getResultCode(), message)) :
                    ResponseEntity.unprocessableEntity().body(SimpleResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Extraction failed: " + message));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SimpleResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Extraction failed: " + e.getMessage()));
        }
    }

    static EpisyncDocument getInstance(InputStreamSource file, Type type) throws IOException {
        switch (type) {
            case MMG:
                return new MmgDocument(file.getInputStream());
            case NND:
                return new NndDocument(file.getInputStream());
            default:
                throw new IllegalArgumentException("Illegal document type");
        }
    }
}
