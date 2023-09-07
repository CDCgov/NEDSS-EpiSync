package gov.cdc.episync.publish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"gov.cdc.episync", "gov.cdc.nbs"})
public class PublishApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublishApplication.class, args);
    }
}
