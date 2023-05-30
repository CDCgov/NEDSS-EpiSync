package com.episync.publish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:mmg-mapping.properties")
@ConfigurationProperties
public class XMLConfigNND {
    private final Map<String, String> nnd = new HashMap<>();
    private final Map<String, String> data = new HashMap<>();

    public Map<String, String> getNnd() {
        return nnd;
    }
    public Map<String, String> getData() {
        return data;
    }

    public String getMmgName(String xmlName) {
        return nnd.get(xmlName);
    }

    public String getDataName(String xmlName) {
        return data.get(xmlName);
    }

}
