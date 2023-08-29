package gov.cdc.nbs.questionbank.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodesetId implements Serializable {
    private String codeSetNm;
    private String classCd;
}
