package gov.cdc.episync.framework;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class EpisyncWriter<T> {

    public File writeToFile(String fileName, T dest)  throws IOException {
        File newFile = new File(System.getProperty("user.home") + File.separator + fileName);
        ObjectWriter writer = Episync.getMapper().writerFor(dest.getClass());
        writer.writeValue(newFile, dest);
        return newFile;
    }
}
