package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.AddressInputDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.Address;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.AuthenticationService;
import com.project.pescueshop.service.UserService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class UserController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/address")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<Address>> addUserAddress(@RequestBody AddressInputDTO dto) throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        Address address = userService.addUserAddress(user, dto);
        ResponseDTO<Address> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, address, "address");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/address")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<List<Address>>> getUserAddress() throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        List<Address> address = userService.getAddressListByUser(user.getUserId());
        ResponseDTO<List<Address>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, address, "addressList");

        return ResponseEntity.ok(result);
    }
}
