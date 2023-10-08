package com.project.pescueshop.controller;

import com.project.pescueshop.dto.AuthenticationDTO;
import com.project.pescueshop.dto.ResponseDTO;
import com.project.pescueshop.dto.UserDTO;
import com.project.pescueshop.model.User;
import com.project.pescueshop.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/userRegister")
    public ResponseEntity<ResponseDTO<UserDTO>> userRegister(@RequestBody UserDTO request){
        return authenticationService.userRegister(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<UserDTO>> authenticate(@RequestBody AuthenticationDTO request){
        return authenticationService.authenticate(request);
    }

    @PostMapping("/authenticateGoogleUser")
    public ResponseEntity<ResponseDTO<UserDTO>> googleUserAuthenticate(@RequestBody UserDTO request){
        return authenticationService.googleUserAuthenticate(request);
    }

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> test(@AuthenticationPrincipal User user) throws Exception {
        User user1 = new User();
        String email = user1.getUserEmail();
        String tes = email.substring(2);
        return null;
    }
}
