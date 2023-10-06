package com.project.pescueshop.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
@Slf4j
@Api
public class TestController {

    @GetMapping("/test")
//    @PreAuthorize("hasAuthority('ROLE_SHELTER_MANAGER')")
//    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getAdoptionApplicationByShelterID() {
        return ResponseEntity.ok("hello");
    }
}
