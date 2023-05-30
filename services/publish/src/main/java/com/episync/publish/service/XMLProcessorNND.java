package com.episync.publish.service;

import com.episync.publish.config.XMLConfigNND;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Qualifier("NNDProcessor")
public class XMLProcessorNND extends AbstractXMLProcessor {
    private final XMLConfigNND xmlConfig;

    public XMLProcessorNND(XMLConfigNND xmlConfig) {
        this.xmlConfig = xmlConfig;
    }

    @Override
    String transform(InputStreamSource xmlFeed) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        StringBuilder csvHead = new StringBuilder();
        StringBuilder csvBody = new StringBuilder();
        JsonNode root = xmlMapper.readTree(xmlFeed.getInputStream());
        return csvHead.append("\n").append(csvBody).toString();
    }

}
