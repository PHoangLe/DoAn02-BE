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
@NoArgsConstructor
@Table(name = "IMPORT_INVOICE")
@Entity
@Builder
@Name(prefix = "IMIV")
public class ImportInvoice {
    @Id
    private String importInvoiceId;
    private String userId;
    private Date createdDate;
    private Long totalPrice;
}
