package com.gs.EnergiShare.AuditLog;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "audit_id")
    private Integer id;

    @NotBlank(message = "{auditLog.table_name.notnull}")
    @Size(max = 50, message = "{auditLog.table_name.size}")
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @NotBlank(message = "{auditLog.action_type.notnull}")
    @Size(max = 10, message = "{auditLog.action_type.size}")
    @Column(name = "action_type", nullable = false)
    private String actionType;

    @NotNull(message = "{auditLog.record_id.notnull}")
    @Column(name = "record_id", nullable = false)
    private Integer recordId;

    @Builder.Default
    @Column(name = "action_date", updatable = false)
    private LocalDate actionDate = LocalDate.now();

    @Size(max = 50, message = "{auditLog.user_name.size}")
    @Column(name = "user_name", nullable = false, columnDefinition = "varchar(50) default USER")
    private String userName;
}
