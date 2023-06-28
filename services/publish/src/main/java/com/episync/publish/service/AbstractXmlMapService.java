package com.episync.publish.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractXmlMapService implements XmlMapService {

    private final EpisyncExternalAPIService apiService;
    private final Map<String, Boolean> mmgNames = new LinkedHashMap<>();
    final Map<String, String> mmg = new HashMap<>();
    final Map<String, String> data = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(XmlMapService.class);

    public AbstractXmlMapService(EpisyncExternalAPIService apiService) {
        this.apiService = apiService;
        populateData();
    }

    protected abstract void processNode(JsonNode node, String mmgName);

    private void populateData() {
        logger.info("Calling dictionary service");
        ResponseEntity<String> response = apiService.getDataDictionary();
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("XML mapping data received");

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(response.getBody());
                for (JsonNode node: root) {
                    String mmgName = node.get("col").textValue();
                    Boolean mayRepeat = node.get("cardinality").textValue().startsWith("Y");
                    mmgNames.put(mmgName, mayRepeat);

                    processNode(node, mmgName);
                }
            } catch (IOException e) {
                logger.error("XML data mapping error", e);
            }
        } else {
            logger.warn("Error when calling data dictionary service: {}", response.getBody());
        }
    }

    @Override
    public Set<String> getMmgNames() {
        return mmgNames.keySet();
    }

    @Override
    public boolean mayRepeat(String mmgName) {
        return mmgNames.get(mmgName);
    }
    @Override
    public String getMmgName(String xmlName) {
        return mmg.get(xmlName);
    }

    @Override
    public String getDataName(String xmlName) {
        return data.get(xmlName);
    }

    @Override
    public boolean hasNndProperty(String key) {
        return mmg.containsKey(key);
    }
}
