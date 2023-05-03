package com.episync.publish.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EpisyncExternalAPIService {

    ResponseEntity<String> validateFeed(MultipartFile file);
}
