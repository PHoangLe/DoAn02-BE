package com.project.pescueshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer addressId;
    private String streetName;
    private String wardName;
    private String districtName;
    private String cityName;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
