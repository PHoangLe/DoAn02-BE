package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.entity.Product;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "product", pluralNoun = "productList")
public class ProductDashboardResult {
    private Product product;
    private Long views;
    private Long buyCount;
}
