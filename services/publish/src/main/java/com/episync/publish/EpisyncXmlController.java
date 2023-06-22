package com.episync.publish;

import com.episync.publish.service.XMLProcessor;
import com.episync.publish.shared.SimpleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="Episync XML Transform")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))}),
        @ApiResponse(responseCode = "422", description = "Unprocessable Entity", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SimpleResponse.class))})
})
@RestController
@RequestMapping("/feed")
public class EpisyncXmlController {
    private final XMLProcessor xmlProcessor;

    public EpisyncXmlController(@Qualifier("NNDProcessor") XMLProcessor xmlProcessor) {
        this.xmlProcessor = xmlProcessor;
    }

    @Operation(summary = "Upload HL7 XML file and convert to MMG v2 CSV feed")
    @PostMapping(value = "/upload/xml", consumes = "multipart/form-data")
    public ResponseEntity<?> postFeedFromXmlFile (@RequestParam("file") MultipartFile file) {
        return xmlProcessor.xmlToCsv(file);
    }


}
