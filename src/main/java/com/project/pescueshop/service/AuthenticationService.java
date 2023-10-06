package com.project.pescueshop.service;

import com.project.pescueshop.dto.AuthenticationDTO;
import com.project.pescueshop.dto.ResponseDTO;
import com.project.pescueshop.dto.UserDTO;
import com.project.pescueshop.entity.User;
import com.project.pescueshop.security.JwtService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public ResponseEntity<ResponseDTO<UserDTO>> userRegister(UserDTO request){
        User user = userService.findByEmail(request.getUserEmail());
        if (user != null){
            if (!user.isLocked()) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.ACCOUNT_EXISTED);
                return ResponseEntity.ok(response);
            }
            else {
                ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.ACCOUNT_INACTIVE);
                return ResponseEntity.ok(response);
            }
        }
        user = new User(request);

        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setIsSocial(false);

        user = userService.addUser(user);

        ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.CREATED_ACCOUNT_SUCCESSFUL, new UserDTO(user));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponseDTO<UserDTO>> authenticate(AuthenticationDTO request){
        try{
            Authentication authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserEmail(),
                            request.getUserPassword()
                    )
            );

            User user = userService.findByEmail(request.getUserEmail());

            if (user.isLocked()) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.ACCOUNT_INACTIVE);
                return ResponseEntity.ok(response);
            }

            if (user.isDeleted()) {
                ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.ACCOUNT_NOT_FOUND);
                return ResponseEntity.ok(response);
            }

            var jwtToken = jwtService.generateJwtToken(user);
            log.trace("Successfully authenticate user: " + request.getUserEmail());


            ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.AUTHENTICATE_SUCCESSFUL, new UserDTO(user, jwtToken));
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e){
            ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.BAD_CREDENTIAL);
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<ResponseDTO<UserDTO>> googleUserAuthenticate(UserDTO request){
        User user = userService.findByEmail(request.getUserEmail());

        if (user == null){
            user = new User(request);
            user.setIsSocial(true);

            user = userService.addUser(user);
        }

        var jwtToken = jwtService.generateJwtToken(user);
        log.trace("Successfully authenticate user: " + request.getUserEmail());

        ResponseDTO<UserDTO> response = new ResponseDTO<>(EnumResponseCode.AUTHENTICATE_SUCCESSFUL, new UserDTO(user, jwtToken));
        return ResponseEntity.ok(response);
    }
}