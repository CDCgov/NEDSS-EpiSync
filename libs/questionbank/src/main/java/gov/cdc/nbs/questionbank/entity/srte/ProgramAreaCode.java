package gov.cdc.nbs.questionbank.entity.srte;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "Program_area_code")
public class ProgramAreaCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prog_area_cd", nullable = false)
    private String progAreaCd;

    @Column(name = "prog_area_desc_txt")
    private String progAreaDescTxt;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "status_cd")
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Column(name = "code_seq")
    private Integer codeSeq;

}
