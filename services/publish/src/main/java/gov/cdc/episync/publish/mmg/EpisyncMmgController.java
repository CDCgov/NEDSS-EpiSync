package gov.cdc.episync.publish.mmg;

import gov.cdc.episync.framework.EpisyncDocumentType;
import gov.cdc.episync.publish.mmg.service.EpisyncMmgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name="Episync MMG Publishing")
@RequestMapping("/mmg")
@RestController
public class EpisyncMmgController {

    private final EpisyncMmgService mmgService;

    public EpisyncMmgController(EpisyncMmgService mmgService) {
        this.mmgService = mmgService;
    }

    @PostMapping(value = "/publish", consumes = "multipart/form-data")
    public ResponseEntity<?> publishMmgJson (
            @RequestParam("file") MultipartFile file,
            @RequestParam EpisyncDocumentType.Type type,
            @RequestParam(defaultValue = "https://ndc.services.cdc.gov") String url) {
        return mmgService.process(file, type, url);
    }
}
