package com.project.pescueshop.model.entity;

import com.project.pescueshop.model.annotation.Name;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VARIETY")
@Entity
@Name(prefix = "VARI")
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
}
