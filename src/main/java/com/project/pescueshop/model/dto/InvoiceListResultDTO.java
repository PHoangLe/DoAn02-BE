package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.entity.Invoice;
import com.project.pescueshop.model.entity.User;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "invoice", pluralNoun = "invoiceList")
public class InvoiceListResultDTO {
    private Invoice invoice;
    private String userName;
}
