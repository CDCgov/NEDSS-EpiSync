package com.episync.publish.service;

import java.util.Set;

public interface XmlMapService {

    String getMmgName(String xmlName);
    String getDataName(String xmlName);
    Set<String> getMmgNames();
    boolean hasNndProperty(String key);
    boolean mayRepeat(String mmgName);
}
