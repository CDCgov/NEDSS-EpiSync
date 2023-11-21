package gov.cdc.nbs.questionbank.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "WA_UI_metadata")
public class WaUiMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_ui_metadata_uid", nullable = false)
    private Long waUiMetadataUid;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "nbs_ui_component_uid", nullable = false)
    private Long nbsUiComponentUid;

    @Column(name = "parent_uid")
    private Long parentUid;

    @Column(name = "question_label")
    private String questionLabel;

    @Column(name = "question_tool_tip")
    private String questionToolTip;

    @Column(name = "enable_ind")
    private String enableInd;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "display_ind")
    private String displayInd;

    @Column(name = "order_nbr")
    private Integer orderNbr;

    @Column(name = "required_ind")
    private String requiredInd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "record_status_cd")
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "max_length")
    private Long maxLength;

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "field_size")
    private String fieldSize;

    @Column(name = "future_date_ind_cd")
    private String futureDateIndCd;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd")
    private String dataCd;

    @Column(name = "data_location")
    private String dataLocation;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "data_use_cd")
    private String dataUseCd;

    @Column(name = "legacy_data_location")
    private String legacyDataLocation;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "question_oid")
    private String questionOid;

    @Column(name = "question_oid_system_txt")
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier")
    private String questionUnitIdentifier;

    @Column(name = "repeats_ind_cd")
    private String repeatsIndCd;

    @Column(name = "unit_parent_identifier")
    private String unitParentIdentifier;

    @Column(name = "group_nm")
    private String groupNm;

    @Column(name = "sub_group_nm")
    private String subGroupNm;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "mask")
    private String mask;

    @Column(name = "entry_method")
    private String entryMethod;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "publish_ind_cd")
    private String publishIndCd;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_question_ind_cd")
    private String standardQuestionIndCd;

    @Column(name = "standard_nnd_ind_cd")
    private String standardNndIndCd;

    @Column(name = "question_nm")
    private String questionNm;

    @Column(name = "unit_type_cd")
    private String unitTypeCd;

    @Column(name = "unit_value")
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private String otherValueIndCd;

    @Column(name = "batch_table_appear_ind_cd")
    private String batchTableAppearIndCd;

    @Column(name = "batch_table_header")
    private String batchTableHeader;

    @Column(name = "batch_table_column_width")
    private Integer batchTableColumnWidth;

    @Column(name = "coinfection_ind_cd")
    private String coinfectionIndCd;

    @Column(name = "block_nm")
    private String blockNm;

    public static WaUiMetadata clone(WaUiMetadata original) {
        return new WaUiMetadata(
                null,
                null,
                original.getNbsUiComponentUid(),
                original.getParentUid(),
                original.getQuestionLabel(),
                original.getQuestionToolTip(),
                original.getEnableInd(),
                original.getDefaultValue(),
                original.getDisplayInd(),
                original.getOrderNbr(),
                original.getRequiredInd(),
                original.getAddTime(),
                original.getAddUserId(),
                original.getLastChgTime(),
                original.getLastChgUserId(),
                original.getRecordStatusCd(),
                original.getRecordStatusTime(),
                original.getMaxLength(),
                original.getAdminComment(),
                original.getVersionCtrlNbr(),
                original.getFieldSize(),
                original.getFutureDateIndCd(),
                original.getLocalId(),
                original.getCodeSetGroupId(),
                original.getDataCd(),
                original.getDataLocation(),
                original.getDataType(),
                original.getDataUseCd(),
                original.getLegacyDataLocation(),
                original.getPartTypeCd(),
                original.getQuestionGroupSeqNbr(),
                original.getQuestionIdentifier(),
                original.getQuestionOid(),
                original.getQuestionOidSystemTxt(),
                original.getQuestionUnitIdentifier(),
                original.getRepeatsIndCd(),
                original.getUnitParentIdentifier(),
                original.getGroupNm(),
                original.getSubGroupNm(),
                original.getDescTxt(),
                original.getMask(),
                original.getEntryMethod(),
                original.getQuestionType(),
                original.getPublishIndCd(),
                original.getMinValue(),
                original.getMaxValue(),
                original.getStandardQuestionIndCd(),
                original.getStandardNndIndCd(),
                original.getQuestionNm(),
                original.getUnitTypeCd(),
                original.getUnitValue(),
                original.getOtherValueIndCd(),
                original.getBatchTableAppearIndCd(),
                original.getBatchTableHeader(),
                original.getBatchTableColumnWidth(),
                original.getCoinfectionIndCd(),
                original.getBlockNm());
    }
}
