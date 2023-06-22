package com.episync.publish.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@PropertySource("classpath:mmg-mapping.properties")
@ConfigurationProperties
public class XMLConfigNND {
    private final Map<String, String> nnd = new HashMap<>();
    private final Map<String, String> data = new HashMap<>();
    private final Map<String, String> rep = new HashMap<>();

    public Map<String, String> getNnd() {
        return nnd;
    }
    public Map<String, String> getData() {
        return data;
    }
    public Map<String, String> getRep() {
        return rep;
    }

    public String getMmgName(String xmlName) {
        return nnd.get(xmlName);
    }

    public String getDataName(String xmlName) {
        return data.get(xmlName);
    }

    public List<String> getMmgNames() {
        return nnd.values().stream().distinct().collect(Collectors.toList());
    }

    public boolean hasNndProperty(String key) {
        return nnd.containsKey(key);
    }

    public boolean mayRepeat(String mmgName) {
        return rep.containsKey(mmgName);
    }

}
