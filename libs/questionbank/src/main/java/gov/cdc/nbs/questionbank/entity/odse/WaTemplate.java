package gov.cdc.nbs.questionbank.entity.odse;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "WA_template")
public class WaTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "template_type", nullable = false)
    private String templateType;

    @Column(name = "xml_payload")
    private String xmlPayload;

    @Column(name = "publish_version_nbr")
    private Integer publishVersionNbr;

    @Column(name = "form_cd")
    private String formCd;

    @Column(name = "condition_cd")
    private String conditionCd;

    @Column(name = "bus_obj_type", nullable = false)
    private String busObjType;

    @Column(name = "datamart_nm")
    private String datamartNm;

    @Column(name = "record_status_cd", nullable = false)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "template_nm")
    private String templateNm;

    @Column(name = "publish_ind_cd")
    private Character publishIndCd;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "nnd_entity_identifier")
    private String nndEntityIdentifier;

    @Column(name = "parent_template_uid")
    private Long parentTemplateUid;

    @Column(name = "source_nm")
    private String sourceNm;

    @Column(name = "template_version_nbr")
    private Integer templateVersionNbr;

    @Column(name = "version_note")
    private String versionNote;

}
