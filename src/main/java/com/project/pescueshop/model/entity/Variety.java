package com.project.pescueshop.model.entity;

import com.project.pescueshop.model.annotation.Name;
import com.project.pescueshop.model.dto.VarietyDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VARIETY")
@Entity
@Name(prefix = "VARI")
@Builder
public class Variety {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.project.pescueshop.util.CustomIdGenerator")
    private String varietyId;
    private String productId;
    private String name;
    @ElementCollection
    private List<String> images;
    private long price;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "VARIETIES_ATTRIBUTES",
            joinColumns = @JoinColumn(name = "varietyId", referencedColumnName = "varietyId"),
            inverseJoinColumns = @JoinColumn(name = "attributeId", referencedColumnName = "attributeId")
    )
    private List<VarietyAttribute> varietyAttributes;
    private String status;

    public Variety(VarietyDTO dto){
        this.varietyId = dto.getVarietyId();
        this.productId = dto.getProductId();
        this.name = dto.getName();
        this.images = dto.getImages();
        this.price = dto.getPrice();
        this.varietyAttributes = dto.getVarietyAttributes();
        this.status = dto.getStatus();
    }

    public Variety(String productId, String name, String image, Long price, String status){
        this.productId = productId;
        this.name = name;
        this.addImage(image);
        this.price = price;
        this.status = status;
    }

    public void addImage(String image){
        if (this.images == null) {
            this.images = new ArrayList<>();
        }

        this.images.add(image);
    }
}
