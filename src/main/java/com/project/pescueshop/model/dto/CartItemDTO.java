package com.project.pescueshop.model.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private String cartItemId;
    private String varietyId;
    private Integer quantity;
    private Long totalItemPrice;
    private Boolean isSelected;
}
