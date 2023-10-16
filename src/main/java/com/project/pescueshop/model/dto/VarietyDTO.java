package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "variety", pluralNoun = "varietyList")
public class VarietyDTO {
    private String varietyId;
    private String productId;
    private String name;
    private List<String> images;
    private long price;
    private List<String> varietyAttributeIdList;
    private List<VarietyAttribute> varietyAttributes;

    public VarietyDTO(Variety variety){
        this.varietyId = variety.getVarietyId();
        this.productId = variety.getProductId();
        this.name = variety.getName();
        this.images = variety.getImages();
        this.price = variety.getPrice();
        this.varietyAttributes = variety.getVarietyAttributes();
    }
}
