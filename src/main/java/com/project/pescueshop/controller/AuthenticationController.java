package com.project.pescueshop.controller;

import com.project.pescueshop.dto.AuthenticationDTO;
import com.project.pescueshop.dto.ResponseDTO;
import com.project.pescueshop.dto.UserDTO;
import com.project.pescueshop.service.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
