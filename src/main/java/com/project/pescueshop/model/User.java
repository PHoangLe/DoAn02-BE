package com.project.pescueshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pescueshop.dto.UserDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userEmail;
    @JsonIgnore
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userAvatar;
    private Boolean isSocial;
    private String status = "IN_ACTIVE";
    private Integer mainAddressId;
    private Integer memberPoint;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    )
    private List<Role> userRoles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Address> addressList;

    public User(UserDTO dto){
        this.userEmail = dto.getUserEmail();
        this.userPassword = dto.getUserPassword();
        this.userFirstName = dto.getUserFirstName();
        this.userLastName = dto.getUserLastName();
        this.userPhoneNumber = dto.getUserPhoneNumber();
        this.userAvatar = dto.getUserAvatar();
        this.status = dto.getStatus();
        this.memberPoint = dto.getMemberPoint();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        userRoles.forEach(_role -> {
            roles.add(new SimpleGrantedAuthority(_role.getRoleName()));
        });
        return roles;
    }
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getUserPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isLocked(){return this.status == "LOCKED";}
    public boolean isInActive(){return this.status == "IN_ACTIVE";}
    public boolean isDeleted(){return this.status == "DELETED";}
}