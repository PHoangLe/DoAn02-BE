package com.project.pescueshop.dto;

import com.project.pescueshop.entity.Address;
import com.project.pescueshop.entity.Role;
import com.project.pescueshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer userId;
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userFullName;
    private String userPhoneNumber;
    private String userAvatar;
    private Boolean isSocial;
    private String status;
    private String jwtToken;
    private List<Address> addressList;
    private List<Role> userRoles;

    public UserDTO(User user){
        this.userEmail = user.getUserEmail();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        this.userFullName = this.userFirstName + this.userLastName;
        this.userPhoneNumber = user.getUserPhoneNumber();
        this.userAvatar = user.getUserAvatar();
        this.isSocial = user.getIsSocial();
        this.status = user.getStatus();
        this.addressList = user.getAddressList();
        this.userRoles = user.getUserRoles();
    }

    public UserDTO(User user, String jwtToken){
        this.userEmail = user.getUserEmail();
        this.userFirstName = user.getUserFirstName();
        this.userLastName = user.getUserLastName();
        this.userFullName = this.userFirstName + this.userLastName;
        this.userPhoneNumber = user.getUserPhoneNumber();
        this.userAvatar = user.getUserAvatar();
        this.isSocial = user.getIsSocial();
        this.status = user.getStatus();
        this.addressList = user.getAddressList();
        this.userRoles = user.getUserRoles();
        this.jwtToken = jwtToken;
    }
}
