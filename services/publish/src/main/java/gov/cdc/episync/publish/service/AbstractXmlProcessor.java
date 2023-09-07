package gov.cdc.episync.publish.service;

import gov.cdc.episync.publish.shared.CsvWriterBean;
import gov.cdc.episync.publish.shared.SimpleResponse;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class AbstractXmlProcessor implements XmlProcessor {

    private final CsvWriterBean writerBean;
    protected final XmlMapService mapService;

    public AbstractXmlProcessor(CsvWriterBean writerBean, XmlMapService mapService) {
        this.writerBean = writerBean;
        this.mapService = mapService;
    }

    @Override
    public ResponseEntity<?> xmlToCsv(InputStreamSource xmlFile) {
        try {
            String[][] csvContent = transform(xmlFile);
            String fileName = "episync-" + System.currentTimeMillis() + ".csv";
            File newFile = new File(System.getProperty("user.home") + File.separator + fileName);

            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                writerBean.writeDataToStream(csvContent, outputStream);
            }
            return ResponseEntity.created(newFile.toURI()).body(SimpleResponse.of(HttpStatus.CREATED.value(), "Transform to CSV: success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(SimpleResponse.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Transform to CSV failed: " + e.getMessage()));
        }
    }

    abstract String[][] transform(InputStreamSource xmlFeed) throws IOException;

}
