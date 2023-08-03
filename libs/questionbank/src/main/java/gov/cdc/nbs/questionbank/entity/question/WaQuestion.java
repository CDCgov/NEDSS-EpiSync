package gov.cdc.nbs.questionbank.entity.question;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Where(clause = "data_type in ('DATE', 'TEXT', 'NUMERIC', 'CODED')")
@Table(name = "WA_question")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type", discriminatorType = DiscriminatorType.STRING)
public abstract class WaQuestion {

    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";

    @Id
    @Column(name = "wa_question_uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_cd", length = 50)
    private String dataCd;

    @Column(name = "data_location", length = 150)
    private String dataLocation;

    @Column(name = "question_identifier", nullable = false, length = 50)
    private String questionIdentifier;

    public void setQuestionIdentifier(String questionIdentifier) {
        this.questionIdentifier = requireNonNull(questionIdentifier, "Question Identifier must not be null");
    }

    @Column(name = "question_oid", length = 150)
    private String questionOid;

    @Column(name = "question_oid_system_txt", length = 100)
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier", length = 20)
    private String questionUnitIdentifier;

    @Column(name = "data_use_cd", length = 20)
    private String dataUseCd;

    @Column(name = "question_label", length = 300)
    private String questionLabel;

    public void setQuestionLabel(String label) {
        this.questionLabel = requireNonNull(label, "Question label must not be null");
    }

    @Column(name = "question_tool_tip", length = 2000)
    private String questionToolTip;

    public void setQuestionToolTip(String tooltip) {
        this.questionToolTip = requireNonNull(tooltip, "Question tooltip must not be null");
    }

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnNm;

    public void setRdbColumnNm(String rdbColumnNm) {
        this.rdbColumnNm = formatAndValidateReportingField(rdbColumnNm);
    }

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "unit_parent_identifier", length = 20)
    private String unitParentIdentifier;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "future_date_ind_cd")
    private Character futureDateIndCd;

    @Column(name = "legacy_data_location", length = 150)
    private String legacyDataLocation;

    @Column(name = "repeats_ind_cd")
    private Character repeatsIndCd;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "question_nm", length = 50)
    private String questionNm;

    public void setQuestionNm(String questionName) {
        this.questionNm = requireNonNull(questionName, "Question Name must not be null");
    }

    @Column(name = "group_nm", length = 50)
    private String groupNm;

    @Column(name = "sub_group_nm", length = 50)
    private String subGroupNm;

    public void setSubGroupNm(String subgroup) {
        this.subGroupNm = requireNonNull(subgroup, "Question subgroup name must not be null");
    }

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    public void setDescTxt(String descTxt) {
        this.descTxt = requireNonNull(descTxt, "Description must not be null");
    }

    @Column(name = "rpt_admin_column_nm", length = 50)
    private String rptAdminColumnNm;

    @Column(name = "nnd_msg_ind")
    private Character nndMsgInd;

    @Column(name = "question_identifier_nnd", length = 50)
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd", length = 150)
    private String questionLabelNnd;

    @Column(name = "question_required_nnd")
    private Character questionRequiredNnd;

    @Column(name = "question_data_type_nnd", length = 10)
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field", length = 30)
    private String hl7SegmentField;

    @Column(name = "order_group_id", length = 5)
    private String orderGroupId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    public void setNbsUiComponentUid(Long displayControl) {
        this.nbsUiComponentUid = requireNonNull(displayControl, "Question display control must not be null");
    }

    @Column(name = "standard_question_ind_cd")
    private Character standardQuestionIndCd;

    @Column(name = "entry_method", length = 20)
    private String entryMethod;

    @Column(name = "question_type", length = 20)
    private String questionType;

    public void setQuestionType(String questionCodeset) {
        this.questionType = requireNonNull(questionCodeset, "CodeSet must not be null");
    }

    @Column(name = "admin_comment", length = 2000)
    private String adminComment;

    @Column(name = "rdb_table_nm", length = 30)
    private String rdbTableNm;

    @Column(name = "user_defined_column_nm", length = 30)
    private String userDefinedColumnNm;

    public void setUserDefinedColumnNm(String userDefinedColumnNm) {
        this.userDefinedColumnNm = formatAndValidateReportingField(userDefinedColumnNm);
    }

    @Column(name = "standard_nnd_ind_cd")
    private Character standardNndIndCd;

    @Column(name = "legacy_question_identifier", length = 50)
    private String legacyQuestionIdentifier;

    @Column(name = "other_value_ind_cd")
    private Character otherValueIndCd;

    @Column(name = "source_nm", length = 250)
    private String sourceNm;

    @Column(name = "coinfection_ind_cd")
    private Character coinfectionIndCd;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;


    private String formatAndValidateReportingField(String s) {
        if (s == null) {
            return null;
        }
        validateAlphaNumericAndUnderscoreOnly(s);
        return s.toUpperCase().trim();
    }

    /**
     * Verify a string only contains alphanumeric or underscore characters
     *
     * @param s
     */
    private void validateAlphaNumericAndUnderscoreOnly(String s) {
        if (!s.matches("^[\\w]*$")) {
            throw new IllegalArgumentException(
                    "Invalid characters specified for WaQuestion field. " +
                            " Only alphanumeric and underscore characters are supported for RDB Column Name, and Data Mart Column Name."
                            + " Supplied value: " + s);
        }
    }

}
