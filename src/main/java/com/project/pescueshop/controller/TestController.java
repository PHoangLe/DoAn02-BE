package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.FileUploadService;
import com.project.pescueshop.service.ProductService;
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

    private final ProductService productService;

    @GetMapping("")
    public Object findAllProduct() throws FriendlyException {
//        ProductDTO url = productService.addVarietyAttribute(null, null);

        return ResponseEntity.ok("url");
    }
}
