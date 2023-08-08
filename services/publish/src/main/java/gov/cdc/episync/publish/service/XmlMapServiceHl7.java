package gov.cdc.episync.publish.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MappingHl7")
public class XmlMapServiceHl7 extends AbstractXmlMapService {

    public XmlMapServiceHl7(EpisyncExternalAPIService apiService) {
        super(apiService);
    }

    @Override
    protected void processNode(JsonNode node, String mmgName) {
        String xmlc = node.get("xmlc").textValue();
        if (!xmlc.isEmpty()) {
            mmg.put(xmlc, mmgName);
        }
    }
}
