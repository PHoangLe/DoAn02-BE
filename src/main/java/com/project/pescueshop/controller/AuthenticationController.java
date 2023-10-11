package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.AuthenticationDTO;
import com.project.pescueshop.model.dto.RegisterDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.dto.UserDTO;
import com.project.pescueshop.model.entity.Role;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.RoleRepository;
import com.project.pescueshop.service.AuthenticationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO<UserDTO>> authenticate(@RequestBody AuthenticationDTO request) throws FriendlyException {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/userRegister")
    public ResponseEntity<ResponseDTO<UserDTO>> userRegister(@RequestBody RegisterDTO request) throws FriendlyException {
        request.setIsSocial(false);
        return authenticationService.userRegister(request);
    }

    @PostMapping("/authenticateGoogleUser")
    public ResponseEntity<ResponseDTO<UserDTO>> googleUserAuthenticate(@RequestBody RegisterDTO request){
        request.setIsSocial(true);
        return authenticationService.googleUserAuthenticate(request);
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test(@AuthenticationPrincipal User user) throws Exception {
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CUSTOMER"));
        return null;
    }
}
