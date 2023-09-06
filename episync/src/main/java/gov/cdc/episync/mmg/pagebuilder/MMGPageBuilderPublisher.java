package gov.cdc.episync.mmg.pagebuilder;

import gov.cdc.episync.framework.*;
import gov.cdc.episync.framework.EpisyncPublishResult.PublishResultCode;
import gov.cdc.episync.mmg.MMGDocument;
import gov.cdc.episync.mmg.MMGPublisher;
import gov.cdc.episync.mmg.MmgData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Dictionary;

@Service("mmg") @RequiredArgsConstructor
public class MMGPageBuilderPublisher extends MMGPublisher {
    private final MMGPageBuilderRouter router;

    @Override
    public EpisyncPublishResult publishDocument(MMGDocument document) throws EpisyncPublishException {
        MmgTemplate template = getTemplate(document.getJson());

        try {
            EpisyncRouteResult routeResult = router.routeData(buildData(template));
            return new EpisyncPublishResult(PublishResultCode.SUCCESS, routeResult.getResultMessage());
        } catch (EpisyncRouterException e) {
            return new EpisyncPublishResult(PublishResultCode.FAILED);
        }
    }

    private MmgTemplate getTemplate(InputStream stream) {
        try {
            return Episync.getMapper().readValue(stream, MmgTemplate.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EpisyncData<String, String> buildData(MmgTemplate template) {
        MmgData<String, String> episyncData = new MmgData<>();
        Dictionary<String, String> data = episyncData.getData();

        data.put("type", template.getType());
        data.put("name", template.getName());
        data.put("shortName", template.getShortName());
        data.put("description", template.getDescription());
        data.put("profileIdentifier", template.getProfileIdentifier());

        return episyncData;

    }
}
