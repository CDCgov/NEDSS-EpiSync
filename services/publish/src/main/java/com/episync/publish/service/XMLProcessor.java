package com.episync.publish.service;

import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;

public interface XMLProcessor {
    ResponseEntity<?> xmlToCsv(InputStreamSource xmlFile);
}
