package gov.cdc.episync.publish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:mmg-mapping.properties")
@ConfigurationProperties
public class XmlConfig {
    private final Map<String, String> xml = new HashMap<>();
    public final static String REMOVABLE = "-";

    public Map<String, String> getXml() {
        return xml;
    }

    public String getXmlName(String xmlName) {
        return xml.get(xmlName);
    }
}
