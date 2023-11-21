package gov.cdc.episync.publish.mmg;

import gov.cdc.episync.framework.EpisyncDocumentType;
import gov.cdc.episync.publish.mmg.service.EpisyncMmgService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Tag(name="Episync MMG Publishing")
@RequestMapping("/mmg")
@RestController
public class EpisyncMmgController {

    private final EpisyncMmgService mmgService;

    public EpisyncMmgController(EpisyncMmgService mmgService) {
        this.mmgService = mmgService;
    }

    @Parameter(name = "uid", description = "UID of the template used as the source page")
    @PostMapping(value = "/publish", consumes = "multipart/form-data")
    public ResponseEntity<?> publishMmgJson (
            @Parameter(description = "MMG json source file") @RequestParam("file") MultipartFile file,
            @RequestParam EpisyncDocumentType.Type type,
            @RequestParam Optional<Long> uid,
            @RequestParam(defaultValue = "https://ndc.services.cdc.gov/message-mapping-guides") String url) {
        return mmgService.process(file, type, uid, url);
    }
}
