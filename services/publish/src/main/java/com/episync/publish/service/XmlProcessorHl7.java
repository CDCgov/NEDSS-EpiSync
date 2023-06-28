package com.episync.publish.service;

import com.episync.publish.config.XmlConfig;
import com.episync.publish.shared.CsvWriterBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

@Service
@Qualifier("ProcessorHl7")
public class XmlProcessorHl7 extends AbstractXmlProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlConfig xmlConfig;

    public XmlProcessorHl7(XmlConfig xmlConfig, CsvWriterBean writerBean, @Qualifier("MappingHl7") XmlMapService mapService) {
        super(writerBean, mapService);
        this.xmlConfig = xmlConfig;
    }

    @Override
    String[][] transform(InputStreamSource xmlFeed) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, JsonNode> map = new HashMap<>();
        JsonNode root = xmlMapper.readTree(xmlFeed.getInputStream());
        traverseXml(root, map, StringUtils.EMPTY);

        Collection<String> mmgNames = mapService.getMmgNames();
        String[][] csvContent = new String[2][mmgNames.size()];

        int i = 0;
        for (String mmgName: mmgNames) {
            csvContent[0][i] = mmgName;

            JsonNode node = map.computeIfAbsent(mmgName, k-> NullNode.getInstance());
            if (node.isNull()) {
                csvContent[1][i] = "";

            } else if (node.isValueNode()) {
                csvContent[1][i] = node.textValue();
            } else {
                csvContent[1][i] = node.toString();
            }
            i++;
        }

        return csvContent;
    }

    private void traverseXml(JsonNode node, Map<String, JsonNode> map, String parent) {
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Entry<String, JsonNode> jsonField = fields.next();
            JsonNode child = jsonField.getValue();
            String name = jsonField.getKey();
            String mmgName = mapService.getMmgName(parent+"."+name);
            if (mmgName != null) {
                processNode(child, mmgName, map);
            }
            if (!child.isValueNode()) {
                traverseXml(child, map, name);
            }
        }
    }

    private void processNode(JsonNode node, String mmgName, Map<String, JsonNode> map) {
        if (node.isArray()) {
            ArrayNode arrayNode = objectMapper.createArrayNode();
            for (JsonNode arrayItem : node) {
                arrayNode.add(processNode(arrayItem));
            }
            map.put(mmgName, arrayNode);

        } else if (node.isObject()){
            map.put(mmgName, processNode(node));
        } else {
            map.put(mmgName, node);
        }
    }

    private JsonNode processNode(JsonNode node) {
        if (node.isObject()) {
            ObjectNode newNode = objectMapper.createObjectNode();
            Iterator<Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Entry<String, JsonNode> jsonField = fields.next();
                String key = jsonField.getKey();
                String newName = xmlConfig.getXmlName(key);
                if (!XmlConfig.REMOVABLE.equals(newName)) {
                    JsonNode child = processNode(jsonField.getValue());
                    newNode.set(newName != null ? newName : key, child);
                }
            }
            return newNode;
        } else {
            return node;
        }
    }
}
