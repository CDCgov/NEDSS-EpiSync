package gov.cdc.episync.publish.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("MappingNnd")
public class XmlMapServiceNnd extends AbstractXmlMapService {

    public XmlMapServiceNnd(EpisyncExternalAPIService apiService) {
        super(apiService);
    }

    @Override
    protected void processNode(JsonNode node, String mmgName) {
        String xmlc = node.get("xmlc").textValue();
        String xmld = node.get("xmld").textValue();
        if (!xmlc.isEmpty()) {
            String[] xmlCol = xmlc.split("\n");
            String[] xmlData = xmld.split("\n");

            if (xmlCol.length == xmlData.length) {
                for (int i = 0; i < xmlCol.length; i++) {
                    mmg.put(xmlCol[i], mmgName);
                    data.put(xmlCol[i], xmlData[i]);
                }
            }
        }
    }
}
