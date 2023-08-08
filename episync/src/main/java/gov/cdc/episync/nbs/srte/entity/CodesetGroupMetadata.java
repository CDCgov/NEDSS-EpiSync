package gov.cdc.episync.nbs.srte.entity;

import javax.persistence.*;

@Entity
@Table(name = "Codeset_Group_Metadata")
public class CodesetGroupMetadata {
    @Column(name = "code_set_nm")
    private String codeSetNm;

    @Id
    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "vads_value_set_code")
    private String vadsValueSetCode;

    @Column(name = "code_set_desc_txt")
    private String codeSetDescTxt;

    @Column(name = "code_set_short_desc_txt")
    private String codeSetShortDescTxt;

    @Column(name = "ldf_picklist_ind_cd")
    private char ldfPicklistIndCd;

    @Column(name = "phin_std_val_ind")
    private char phinStdValInd;

    public String getCodeSetNm() {
        return this.codeSetNm;
    }

    public void setCodeSetNm(String codeSetNm) {
        this.codeSetNm = codeSetNm;
    }

    public Long getCodeSetGroupId() {
        return this.codeSetGroupId;
    }

    public void setCodeSetGroupId(Long codeSetGroupId) {
        this.codeSetGroupId = codeSetGroupId;
    }

    public String getVadsValueSetCode() {
        return this.vadsValueSetCode;
    }

    public void setVadsValueSetCode(String vadsValueSetCode) {
        this.vadsValueSetCode = vadsValueSetCode;
    }

    public String getCodeSetDescTxt() {
        return this.codeSetDescTxt;
    }

    public void setCodeSetDescTxt(String codeSetDescTxt) {
        this.codeSetDescTxt = codeSetDescTxt;
    }

    public String getCodeSetShortDescTxt() {
        return this.codeSetShortDescTxt;
    }

    public void setCodeSetShortDescTxt(String codeSetShortDescTxt) {
        this.codeSetShortDescTxt = codeSetShortDescTxt;
    }

    public char getLdfPicklistIndCd() {
        return this.ldfPicklistIndCd;
    }

    public void setLdfPicklistIndCd(char ldfPicklistIndCd) {
        this.ldfPicklistIndCd = ldfPicklistIndCd;
    }

    public char getPhinStdValInd() {
        return this.phinStdValInd;
    }

    public void setPhinStdValInd(char phinStdValInd) {
        this.phinStdValInd = phinStdValInd;
    }
}
