package com.project.pescueshop.model.entity;

import com.project.pescueshop.model.annotation.Name;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VARIETY_ATTRIBUTE")
@Entity
@Name(prefix = "VAAT", noun = "varietyAttribute")
public class VarietyAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String attributeId;
    private String type;
    private String name;
    private String value;
}
