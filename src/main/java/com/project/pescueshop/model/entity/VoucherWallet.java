package com.project.pescueshop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VOUCHER_WALLET")
@Entity
public class VoucherWallet {
    @Id
    private String userId;
    @Id
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "voucherId", referencedColumnName = "voucherId")
    private Voucher voucher;
    private int quantity;
}
