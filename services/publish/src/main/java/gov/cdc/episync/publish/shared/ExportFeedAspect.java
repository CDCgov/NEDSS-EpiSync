package gov.cdc.episync.publish.shared;

import gov.cdc.episync.publish.entity.EpisyncMmg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@Aspect
@Component
public class ExportFeedAspect {

    private final CsvWriterS3Bean writerBean;

    public ExportFeedAspect(CsvWriterS3Bean writerBean) {
        this.writerBean = writerBean;
    }

    @Around("execution(* gov.cdc.episync.publish.EpisyncFeedController.get*(..)) && args(..,export)")
    public Object export(ProceedingJoinPoint joinPoint, @RequestParam(name="export", required=false, defaultValue="false") boolean export) throws Throwable {
        Object returnVal = joinPoint.proceed();
        if (export) {
            try {
                List<EpisyncMmg> data = ((ResponseEntity<List<EpisyncMmg>>) returnVal).getBody();
                URI uri = writerBean.writeDataToS3(data);
                return ResponseEntity.created(uri).body(new SimpleResponse(HttpStatus.CREATED.value(), "Export to S3: success"));
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Export to S3 failed: " + e.getMessage()));
            }
        }

        return returnVal;
    }
}
