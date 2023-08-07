package gov.cdc.episync.publish.service;

import gov.cdc.episync.publish.shared.CsvWriterBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier("ProcessorNnd")
public class XmlProcessorNnd extends AbstractXmlProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public XmlProcessorNnd(CsvWriterBean writerBean, @Qualifier("MappingNnd") XmlMapService mapService) {
        super(writerBean, mapService);
    }

    @Value("${xml.groupId}")
    private String groupId;

    @Override
    String[][] transform(InputStreamSource xmlFeed) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Map<String, List<ColData>> map = new HashMap<>();
        JsonNode root = xmlMapper.readTree(xmlFeed.getInputStream());
        traverseXml(root, StringUtils.EMPTY, map);

        Set<String> mmgNames = mapService.getMmgNames();
        String[][] csvContent = new String[2][mmgNames.size()];

        int i = 0;
        for (String mmgName: mmgNames) {
            csvContent[0][i] = mmgName;
            List<ColData> colData = map.computeIfAbsent(mmgName, k -> Collections.emptyList());
            if (colData.isEmpty()) {
                csvContent[1][i] = "";
            } else {
                Map<String, List<ColData>> hl7Map = colData.stream().collect(Collectors.groupingBy(ColData::getName));
                if (hl7Map.size() > 1) { // many-to-one mapping
                    if (mapService.mayRepeat(mmgName)) {
                        Map<Integer, List<ColData>> groupMap = hl7Map.entrySet().stream().flatMap(e -> e.getValue().stream()).collect(Collectors.groupingBy(ColData::getGroupId));
                        ArrayNode arrayNode = objectMapper.createArrayNode();
                        groupMap.values().forEach(lst -> {
                            ObjectNode node = objectMapper.createObjectNode();
                            lst.stream().sorted(Comparator.comparing(ColData::getName)).forEach(cd -> {
                                JsonNode jsonNode = trimNode(cd.getNode());
                                node.put(cd.getName(), jsonNode.size() > 1 ? jsonNode.toString() : jsonNode.textValue());
                            });
                            arrayNode.add(node);
                        });
                        csvContent[1][i] = arrayNode.toString();
                    } else {
                        ObjectNode node = objectMapper.createObjectNode();
                        hl7Map.entrySet().stream().flatMap(e -> e.getValue().stream())
                                .sorted(Comparator.comparing(ColData::getName)).forEach(cd -> {
                            JsonNode jsonNode = trimNode(cd.getNode());
                            node.put(cd.getName(), jsonNode.size() > 1 ? jsonNode.toString() : jsonNode.textValue());
                        });
                        csvContent[1][i] = node.toString();
                    }

                } else { // one-to-one-mapping
                    List<ColData> lst = hl7Map.values().iterator().next();
                    if (lst.size() > 1) { // repeating column
                        ArrayNode arrayNode = objectMapper.createArrayNode();
                        for (ColData cd: lst) {
                            arrayNode.add(trimNode(cd.getNode()));
                        }
                        csvContent[1][i] = arrayNode.toString();
                    } else {
                        ColData cd = lst.iterator().next();
                        JsonNode jsonNode = trimNode(cd.getNode());
                        csvContent[1][i] = jsonNode.size() > 1 ? jsonNode.toString() : jsonNode.textValue();
                    }
                }
            }
            i++;
        }

        return csvContent;
    }

    private static JsonNode trimNode(JsonNode nd) {
        if (nd.size() > 1) {
            Iterator<JsonNode> it = nd.iterator();
            while (it.hasNext()) {
                JsonNode child = it.next();
                if (child.isNull() || child.textValue().isEmpty())
                    it.remove();
            }
        }

        if (nd.size() > 1) {
            return nd;
        }else {
            return nd.elements().next();
        }
    }

    private Optional<String> traverseXml(JsonNode node, String parent, Map<String, List<ColData>> map) {
        Optional<String> key;

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> jsonField = fields.next();
            JsonNode child = jsonField.getValue();
            String name = jsonField.getKey();

            if (child.isNull()) {
                continue;
            }
            if (child.isArray()) {
                for (JsonNode arrayItem : child) {
                    key = traverseXml(arrayItem, name, map);
                    key.ifPresent(s -> {
                        JsonNode orderNode = arrayItem.findValue(groupId);
                        int group = orderNode.asInt();
                        processNode(arrayItem, name, mapService.getDataName(s), s, group, map);
                    });
                }
            } else if (!child.isValueNode()) {
                key = traverseXml(child, name, map);
                key.ifPresent(s -> {
                    JsonNode orderNode = child.findValue(groupId);
                    int group = orderNode.asInt();
                    processNode(child, name, mapService.getDataName(s), s, group, map);
                });
            } else {
                String path = parent + "." + name + "." + child.textValue();
                if (mapService.hasNndProperty(path)) {
                    return Optional.of(path);
                }
            }
        }
        return Optional.empty();
    }

    private void processNode(JsonNode node, String parent, String dataPath, String colPath,
                             int groupId, Map<String, List<ColData>> map) {

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> jsonField = fields.next();
            JsonNode child = jsonField.getValue();
            String name = jsonField.getKey();
            String path = parent + "." + name;
            if (path.equals(dataPath)) {
                String hl7Seg = colPath.substring(StringUtils.ordinalIndexOf(colPath, ".", 2)+1);
                String mmgCol = mapService.getMmgName(colPath);
                map.computeIfAbsent(mmgCol, mc -> new ArrayList<>()).add(new ColData(hl7Seg, child, groupId));
                return;
            }
            if (!child.isValueNode()) {
                processNode(child, path, dataPath, colPath, groupId, map);
            }
        }

    }
}

@Getter @AllArgsConstructor
class ColData {
    private String name;
    private JsonNode node;
    private int groupId;
}
