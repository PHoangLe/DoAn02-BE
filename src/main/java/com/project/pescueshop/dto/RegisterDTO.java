package com.project.pescueshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.pescueshop.model.Address;
import com.project.pescueshop.model.Role;
import com.project.pescueshop.model.annotation.Name;
import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "new user", pluralNoun = "new users")
public class RegisterDTO {
    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private String userAvatar;
    @JsonIgnore
    private Boolean isSocial;
}
