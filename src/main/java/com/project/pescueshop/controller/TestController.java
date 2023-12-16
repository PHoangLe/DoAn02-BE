package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.ChatRoomService;
import com.project.pescueshop.service.FileUploadService;
import com.project.pescueshop.service.PaymentService;
import com.project.pescueshop.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class TestController {

    private final ChatRoomService chatRoomService;

    @GetMapping("")
    public Object findAllProduct() throws FriendlyException, UnsupportedEncodingException {
//        ProductDTO url = productService.addVarietyAttribute(null, null);
        String chatRoomId = chatRoomService.getChatId("USER_1699792351661_gmNWp", "USER_1697033158735", true).orElse("123");
        return ResponseEntity.ok(chatRoomId);
    }
}
