package gov.cdc.nbs.questionbank.entity.odse;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "WA_NND_metadata")
public class WaNndMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_nnd_metadata_uid", nullable = false)
    private Long waNndMetadataUid;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "question_identifier_nnd")
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd")
    private String questionLabelNnd;

    @Column(name = "question_required_nnd", nullable = false)
    private String questionRequiredNnd;

    @Column(name = "question_data_type_nnd", nullable = false)
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field", nullable = false)
    private String hl7SegmentField;

    @Column(name = "order_group_id")
    private String orderGroupId;

    @Column(name = "translation_table_nm")
    private String translationTableNm;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "record_status_cd", nullable = false)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "xml_path")
    private String xmlPath;

    @Column(name = "xml_tag")
    private String xmlTag;

    @Column(name = "xml_data_type")
    private String xmlDataType;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "repeat_group_seq_nbr")
    private Integer repeatGroupSeqNbr;

    @Column(name = "question_order_nnd")
    private Integer questionOrderNnd;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "wa_ui_metadata_uid", nullable = false)
    private Long waUiMetadataUid;

    @Column(name = "question_map")
    private String questionMap;

    @Column(name = "indicator_cd")
    private String indicatorCd;

}
