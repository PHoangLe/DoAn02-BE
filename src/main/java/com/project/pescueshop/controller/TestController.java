package com.project.pescueshop.controller;

import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.FileUploadService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class TestController {

    private final FileUploadService fileUploadService;

    @PostMapping("")
    public Object findAllProduct(@RequestParam MultipartFile image) throws FriendlyException {
        String url = fileUploadService.uploadFile(image, "test", "test");

        return ResponseEntity.ok(url);
    }
}
