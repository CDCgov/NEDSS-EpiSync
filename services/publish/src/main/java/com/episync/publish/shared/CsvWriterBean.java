package com.episync.publish.shared;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CsvWriterBean {
    public void writeDataToStream(String[][] content, OutputStream out) throws IOException {
        //ByteArrayOutputStream out = new ByteArrayOutputStream();

        // Create the CSV writer
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT.withHeader(content[0]));

        // Write the row data to the CSV
        for (int i = 1; i < content.length; i++) {
            csvPrinter.printRecord((Object[]) content[i]);
        }

        // writing the underlying stream
        csvPrinter.flush();
    }
}
