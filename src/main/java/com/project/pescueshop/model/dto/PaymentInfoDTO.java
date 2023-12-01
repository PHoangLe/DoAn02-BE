package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.entity.Address;
import com.project.pescueshop.model.entity.Voucher;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "paymentInfo", pluralNoun = "paymentInfoList")
public class PaymentInfoDTO {
    private Address address;
    private Voucher voucher;
    private String phoneNumber;
    private String recipientName;
    private String paymentType;
    private String returnUrl;
}
