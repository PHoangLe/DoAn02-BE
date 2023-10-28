package com.project.pescueshop.model.entity;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.dto.VarietyDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
@Entity
@Name(prefix = "PROD")
public class Product {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.project.pescueshop.util.CustomIdGenerator")
    private String productId;
    private String name;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subCategoryId", referencedColumnName = "subCategoryId")
    private SubCategory subCategory;
    private long price;
    private String petType;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "brandId", referencedColumnName = "brandId")
    private Brand brand;
    private String detail;
    private String description;
    @Column(columnDefinition = "0")
    private float avgRating;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId", referencedColumnName = "productId")
    private List<Variety> varieties;
    private String status;
    @ElementCollection
    private List<String> images;

    public Product(ProductDTO dto){
        this.productId = dto.getProductId();
        this.name = dto.getName();
        this.subCategory = dto.getSubCategory();
        this.price = dto.getPrice();
        this.petType = dto.getPetType();
        this.brand = dto.getBrand();
        this.detail = dto.getDetail();
        this.description = dto.getDescription();
        this.avgRating = dto.getAvgRating();
        if (dto.getVarieties()!= null) {
            this.varieties = dto.getVarieties().stream()
                    .map(Variety::new)
                    .toList();
        }
        this.images = dto.getImages();
        this.status = dto.getStatus();
    }

    public void addVariety(Variety variety){
        if (this.varieties == null) {
            this.varieties = new ArrayList<>();
        }

        this.varieties.add(variety);
    }
}
