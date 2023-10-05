package com.project.pescueshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String phoneNo;
    private String userAvatar;
    private String status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    )
    private List<Role> userRoles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Address> addressList;

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
        return this.status == "ACTIVE";
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == "ACTIVE";
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == "ACTIVE";
    }

    @Override
    public boolean isEnabled() {
        return this.status == "ACTIVE";
    }
}