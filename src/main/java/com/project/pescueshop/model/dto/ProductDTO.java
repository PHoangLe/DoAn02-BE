package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.entity.Brand;
import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.SubCategory;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "product", pluralNoun = "productList")
public class ProductDTO {
    private String productId;
    private String name;
    private String subCategoryId;
    private SubCategory subCategory;
    private long price;
    private int petTypeId;
    private String petType;
    private String brandId;
    private Brand brand;
    private String detail;
    private String description;
    private float avgRating;
    private List<VarietyDTO> varieties;
    private String status;

    public ProductDTO(Product product){
        this.productId = product.getProductId();
        this.name = product.getName();
        this.subCategory = product.getSubCategory();
        this.price = product.getPrice();
        this.petType = product.getPetType();
        this.brand = product.getBrand();
        this.detail = product.getDetail();
        this.description = product.getDescription();
        this.avgRating = product.getAvgRating();
        if (product.getVarieties() != null) {
            this.varieties = product.getVarieties().stream()
                    .map(VarietyDTO::new).toList();
        }
        this.status = product.getStatus();
    }
}
