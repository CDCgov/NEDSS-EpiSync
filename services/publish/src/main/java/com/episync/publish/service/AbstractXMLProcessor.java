package com.episync.publish.service;

import com.episync.publish.shared.SimpleResponse;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractXMLProcessor implements XMLProcessor{
    @Override
    public ResponseEntity<?> xmlToCsv(InputStreamSource xmlFile) {
        try {
            String csvContent = transform(xmlFile);
            String fileName = "episync-" + System.currentTimeMillis() + ".csv";
            File newFile = new File(System.getProperty("user.home") + File.separator + fileName);
            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write(csvContent.getBytes());
            }
            return ResponseEntity.created(newFile.toURI()).body(new SimpleResponse(HttpStatus.CREATED.value(), "Transform to CSV: success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new SimpleResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Transform to CSV failed: " + e.getMessage()));
        }
    }

    abstract String transform(InputStreamSource xmlFeed) throws IOException;

}
