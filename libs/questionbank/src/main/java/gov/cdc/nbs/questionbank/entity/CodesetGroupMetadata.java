package gov.cdc.nbs.questionbank.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Codeset_Group_Metadata")
public class CodesetGroupMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "code_set_nm", nullable = false)
    private String codeSetNm;

    @Id
    @Column(name = "code_set_group_id", nullable = false)
    private Long codeSetGroupId;

    @Column(name = "vads_value_set_code")
    private String vadsValueSetCode;

    @Column(name = "code_set_desc_txt")
    private String codeSetDescTxt;

    @Column(name = "code_set_short_desc_txt")
    private String codeSetShortDescTxt;

    @Column(name = "ldf_picklist_ind_cd")
    private String ldfPicklistIndCd;

    @Column(name = "phin_std_val_ind")
    private String phinStdValInd;

}
