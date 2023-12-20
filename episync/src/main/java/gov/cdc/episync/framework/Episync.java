package gov.cdc.episync.framework;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import gov.cdc.episync.framework.EpisyncExtractResult.ExtractResultCode;
import gov.cdc.episync.framework.EpisyncPublishResult.PublishResultCode;
import gov.cdc.episync.pagebuilder.nbs.NbsPageExtractor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service @RequiredArgsConstructor
public class Episync {
    private final EpisyncPublisherFactory publisherFactory;
    private final NbsPageExtractor extractor;

    @Getter
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new AfterburnerModule())
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

    public EpisyncPublishResult publishDocument(EpisyncDocument document) {
        try {
            EpisyncPublisher<? extends EpisyncDocument> publisher = publisherFactory.getPublisherForType(document);
            Method method = publisher.getClass().getMethod("publishDocument", EpisyncDocument.class);
            return  (EpisyncPublishResult) method.invoke(publisher, document);
        } catch (InvocationTargetException t) {
            return new EpisyncPublishResult(PublishResultCode.FAILED, t.getTargetException().toString());
        } catch (Exception e) {
            return new EpisyncPublishResult(PublishResultCode.FAILED, e.toString());
        }
    }

    public EpisyncExtractResult extract(Long uid) {
        try {
            return extractor.extractData(uid);
        } catch (Exception e) {
            return new EpisyncExtractResult(ExtractResultCode.FAILED, e.toString(), null);
        }
    }
}
