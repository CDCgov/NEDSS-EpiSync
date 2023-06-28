package com.episync.publish.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EpisyncExternalAPIServiceImpl implements EpisyncExternalAPIService{
    @Value("${episync.dd.url}/validate")
    private String validateUri;

    @Value("${episync.dd.url}/dictionary")
    private String ddUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getDataDictionary() {
        try {

            ResponseEntity<String> response = restTemplate.getForEntity(ddUri, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(response.getBody());
            }
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> validateFeed(MultipartFile file) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()){
                @Override
                public String getFilename(){
                    return file.getOriginalFilename();
                }
            };
            body.add("file",fileAsResource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(validateUri, httpEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode json = new ObjectMapper().readTree(response.getBody());
                if (!json.get("validates").asBoolean()) {
                    response = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(json.get("message").asText());
                }
            }
            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
