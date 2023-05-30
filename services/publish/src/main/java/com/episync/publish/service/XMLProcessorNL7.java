package com.episync.publish.service;

import com.episync.publish.config.XMLConfigNL7;
import com.episync.publish.shared.SimpleResponse;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Service
@Qualifier("NL7Processor")
public class XMLProcessorNL7 extends AbstractXMLProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XMLConfigNL7 xmlConfig;

    public XMLProcessorNL7(XMLConfigNL7 xmlConfig) {
        this.xmlConfig = xmlConfig;
        objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
    }

    @Override
    String transform(InputStreamSource xmlFeed) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        StringBuilder csvHead = new StringBuilder();
        StringBuilder csvBody = new StringBuilder();
        Map<String, JsonNode> map = new HashMap<>();
        JsonNode root = xmlMapper.readTree(xmlFeed.getInputStream());
        traverseXml(root, map, StringUtils.EMPTY);

        for (String mmgName: xmlConfig.getMmg().values()) {
            csvHead.append(mmgName).append(",");
            JsonNode node = map.computeIfAbsent(mmgName, k-> NullNode.getInstance());
            if (node.isNull()) {
                csvBody.append(",");
            } else if (node.isValueNode()) {
                csvBody.append(node).append(",");
            } else {
                csvBody.append("\"").append(objectMapper.writeValueAsString(node)).append("\",");
            }
        }

        return csvHead.append("\n").append(csvBody).toString();
    }

    private void traverseXml(JsonNode node, Map<String, JsonNode> map, String parent) {
        Iterator<Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Entry<String, JsonNode> jsonField = fields.next();
            JsonNode child = jsonField.getValue();
            String name = jsonField.getKey();
            String mmgName = xmlConfig.getMmgName(parent+"."+name);
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
                if (!XMLConfigNL7.REMOVABLE.equals(newName)) {
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
