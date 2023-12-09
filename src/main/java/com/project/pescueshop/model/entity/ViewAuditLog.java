package com.project.pescueshop.model.entity;

import com.project.pescueshop.model.annotation.Name;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "VIEW_AUDIT_LOG")
@Entity
@Builder
@Name(prefix = "VIAU")
public class ViewAuditLog {
    @Id
    private String viewAuditLogId;
    private String objectId;
    private Date date;
    private String objectType;
}
