package gov.cdc.nbs.questionbank.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "WA_RDB_metadata")
public class WaRdbMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_rdb_metadata_uid", nullable = false)
    private Long waRdbMetadataUid;

    @Column(name = "wa_template_uid", nullable = false)
    private Long waTemplateUid;

    @Column(name = "user_defined_column_nm")
    private String userDefinedColumnNm;

    @Column(name = "record_status_cd", nullable = false)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

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

    @Column(name = "wa_ui_metadata_uid")
    private Long waUiMetadataUid;

    @Column(name = "rdb_table_nm")
    private String rdbTableNm;

    @Column(name = "rpt_admin_column_nm")
    private String rptAdminColumnNm;

    @Column(name = "rdb_column_nm")
    private String rdbColumnNm;

    @Column(name = "question_identifier")
    private String questionIdentifier;

    @Column(name = "block_pivot_nbr")
    private Integer blockPivotNbr;

}
