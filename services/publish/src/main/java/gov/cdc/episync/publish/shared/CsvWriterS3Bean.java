package gov.cdc.episync.publish.shared;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import gov.cdc.episync.publish.entity.EpisyncMmg;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import javax.persistence.Column;

@Component
public class CsvWriterS3Bean {
    @Value("${s3.bucket.pub}")
    private String s3bucket;

    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .build();

    private String[] getHeader() {
        // Extract column names using Reflection from the entity
        Class<EpisyncMmg> mmgClass = EpisyncMmg.class;
        Field[] fields = mmgClass.getDeclaredFields();

        String[] header = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            header[i] = fields[i].getAnnotation(Column.class).name();
        }
        return header;
    }

    private String[] getRowData(EpisyncMmg ecase) {
        Class<EpisyncMmg> mmgClass = EpisyncMmg.class;
        Field[] fields = mmgClass.getDeclaredFields();

        // Extract field values using Reflection from the entity
        String[] rowData = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object value = fields[i].get(ecase);
                rowData[i] = value != null ? value.toString() : "";
            } catch (IllegalAccessException e) {
                // Handle exception as needed
                e.printStackTrace();
            }
        }

        return rowData;
    }

    public URI writeDataToS3(List<EpisyncMmg> data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Create the CSV writer
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(getHeader()));

        // Write the feed data to the CSV
        for (EpisyncMmg entity : data) {
            csvPrinter.printRecord((Object[]) getRowData(entity));
        }

        // writing the underlying stream
        csvPrinter.flush();

        InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        return writeStreamToS3(inputStream, out.size());
    }

    public URI writeStreamToS3(InputStream inputStream, long size) {
        String fileName = "episync-" + System.currentTimeMillis() + ".csv";
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        s3Client.putObject(new PutObjectRequest(s3bucket, fileName, inputStream, objectMetadata));

        return URI.create("s3://" + s3bucket + "/" + fileName);
    }
}
