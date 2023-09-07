package gov.cdc.nbs.questionbank.entity.odse;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "WA_question")
public class WaQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_question_uid", nullable = false)
    private Long waQuestionUid;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd")
    private String dataCd;

    @Column(name = "data_location")
    private String dataLocation;

    @Column(name = "question_identifier", nullable = false)
    private String questionIdentifier;

    @Column(name = "question_oid")
    private String questionOid;

    @Column(name = "question_oid_system_txt")
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier")
    private String questionUnitIdentifier;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_use_cd")
    private String dataUseCd;

    @Column(name = "question_label")
    private String questionLabel;

    @Column(name = "question_tool_tip")
    private String questionToolTip;

    @Column(name = "rdb_column_nm")
    private String rdbColumnNm;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "unit_parent_identifier")
    private String unitParentIdentifier;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "future_date_ind_cd")
    private String futureDateIndCd;

    @Column(name = "legacy_data_location")
    private String legacyDataLocation;

    @Column(name = "repeats_ind_cd")
    private String repeatsIndCd;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "question_nm")
    private String questionNm;

    @Column(name = "group_nm")
    private String groupNm;

    @Column(name = "sub_group_nm")
    private String subGroupNm;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "mask")
    private String mask;

    @Column(name = "field_size")
    private String fieldSize;

    @Column(name = "rpt_admin_column_nm")
    private String rptAdminColumnNm;

    @Column(name = "nnd_msg_ind")
    private String nndMsgInd;

    @Column(name = "question_identifier_nnd")
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd")
    private String questionLabelNnd;

    @Column(name = "question_required_nnd")
    private String questionRequiredNnd;

    @Column(name = "question_data_type_nnd")
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field")
    private String hl7SegmentField;

    @Column(name = "order_group_id")
    private String orderGroupId;

    @Column(name = "record_status_cd", nullable = false)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "standard_question_ind_cd")
    private String standardQuestionIndCd;

    @Column(name = "entry_method")
    private String entryMethod;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "rdb_table_nm")
    private String rdbTableNm;

    @Column(name = "user_defined_column_nm")
    private String userDefinedColumnNm;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_nnd_ind_cd")
    private String standardNndIndCd;

    @Column(name = "legacy_question_identifier")
    private String legacyQuestionIdentifier;

    @Column(name = "unit_type_cd")
    private String unitTypeCd;

    @Column(name = "unit_value")
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private String otherValueIndCd;

    @Column(name = "source_nm")
    private String sourceNm;

    @Column(name = "coinfection_ind_cd")
    private String coinfectionIndCd;

}
