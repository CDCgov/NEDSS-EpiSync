package com.episync.publish.shared;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.episync.publish.entity.EpisyncMmg;
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
public class CsvWriterBean {
    @Value("${s3.bucket.pub}")
    String s3bucket;

    AWSCredentialsProvider credentialsProvider = new ProfileCredentialsProvider("episync");
    private final AmazonS3 s3Client = AmazonS3ClientBuilder.standard() // AmazonS3ClientBuilder.defaultClient();
        .withCredentials(credentialsProvider)
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

    public URI writeCsvToS3(List<EpisyncMmg> cases) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Create the CSV writer
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(getHeader()));

        // Write the feed data to the CSV
        for (EpisyncMmg ecase : cases) {
            csvPrinter.printRecord((Object[]) getRowData(ecase));
        }

        // writing the underlying stream
        csvPrinter.flush();

        String fileName = "episync-" + System.currentTimeMillis() + ".csv";
        InputStream inputStream = new ByteArrayInputStream(out.toByteArray());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(out.toByteArray().length);
        s3Client.putObject(new PutObjectRequest(s3bucket, fileName, inputStream, objectMetadata));

        return URI.create("s3://" + s3bucket + "/" + fileName);
    }
}
