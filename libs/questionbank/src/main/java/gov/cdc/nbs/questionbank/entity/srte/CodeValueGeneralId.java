package gov.cdc.nbs.questionbank.entity.srte;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data @NoArgsConstructor @Embeddable
public class CodeValueGeneralId implements Serializable {
    private String codeSetNm;
    private String code;
}
