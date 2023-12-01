package com.project.pescueshop.controller;

import com.project.pescueshop.config.PaymentConfig;
import com.project.pescueshop.model.dto.PaymentInfoDTO;
import com.project.pescueshop.model.dto.PaymentInputDTO;
import com.project.pescueshop.model.dto.PaymentOutputDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.AuthenticationService;
import com.project.pescueshop.service.PaymentService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class PaymentController {
    private final AuthenticationService authenticationService;
    private final PaymentService paymentService;

    @PostMapping("/user-cart-checkout")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<String>> createPayment(@RequestBody PaymentInfoDTO paymentInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        User user = authenticationService.getCurrentLoggedInUser();
        String paymentUrl = paymentService.userCartCheckout(user, paymentInfoDTO);
        ResponseDTO<String> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, paymentUrl, "output");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/payment-info")
    public ResponseEntity<?> transactions(){

        return null;
    }
}
