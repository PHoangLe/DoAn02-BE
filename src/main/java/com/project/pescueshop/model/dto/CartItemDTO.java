package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Name(noun = "item", pluralNoun = "items")
public class CartItemDTO {
    private String cartItemId;
    private String varietyId;
    private Integer quantity;
    private Long totalItemPrice;
    private Boolean isSelected;
}
