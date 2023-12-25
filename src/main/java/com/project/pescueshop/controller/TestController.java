package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.ChatRoomService;
import com.project.pescueshop.service.FileUploadService;
import com.project.pescueshop.service.PaymentService;
import com.project.pescueshop.service.ProductService;
import com.project.pescueshop.util.Util;
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

    private final PaymentService paymentService;

    @GetMapping("")
    public Object findAllProduct() throws FriendlyException, UnsupportedEncodingException {
//        ProductDTO url = productService.addVarietyAttribute(null, null);
        String key = Util.getRandomKey();
        String chatRoomId = paymentService.createPaymentLink(key, "abc" , 100000);
        return ResponseEntity.ok(chatRoomId);
    }
}
