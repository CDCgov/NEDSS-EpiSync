package gov.cdc.nbs.questionbank.entity.srte;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeValueGeneralId implements Serializable {
    private String codeSetNm;
    private String code;
}
