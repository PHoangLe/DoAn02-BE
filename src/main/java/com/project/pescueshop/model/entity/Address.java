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
@Table(name = "ADDRESS")
@Entity
@Name(prefix = "ADDR")
public class Address {
    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.project.pescueshop.util.CustomIdGenerator")
    private String addressId;
    private String userId;
    private String streetName;
    private String wardName;
    private String districtName;
    private String cityName;
}
