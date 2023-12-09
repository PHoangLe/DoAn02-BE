package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.CartCheckOutInfoDTO;
import com.project.pescueshop.model.dto.InvoiceItemDTO;
import com.project.pescueshop.model.dto.PaymentInfoDTO;
import com.project.pescueshop.model.dto.SingleItemCheckOutInfoDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.Invoice;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/user-cart-checkout")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<String>> cartCheckout(@RequestBody CartCheckOutInfoDTO cartCheckOutInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        User user = AuthenticationService.getCurrentLoggedInUser();
        String paymentUrl = paymentService.userCartCheckoutAuthenticate(user, cartCheckOutInfoDTO);
        ResponseDTO<String> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, paymentUrl, "output");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/single-item-checkout")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<String>> singleItemCheckout(@RequestBody SingleItemCheckOutInfoDTO singleItemCheckOutInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        User user = AuthenticationService.getCurrentLoggedInUser();
        String paymentUrl = paymentService.singleItemCheckOutAuthenticate(user, singleItemCheckOutInfoDTO);
        ResponseDTO<String> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, paymentUrl, "output");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/un-authenticate/user-cart-checkout/{cartId}")
    public ResponseEntity<ResponseDTO<String>> cartCheckoutUnAuthenticate(@RequestBody CartCheckOutInfoDTO cartCheckOutInfoDTO, @PathVariable String cartId) throws FriendlyException, UnsupportedEncodingException {
        String paymentUrl = paymentService.userCartCheckoutUnAuthenticate(cartCheckOutInfoDTO);
        ResponseDTO<String> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, paymentUrl, "output");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/un-authenticate/single-item-checkout")
    public ResponseEntity<ResponseDTO<String>> singleItemCheckoutUnAuthenticate(@RequestBody SingleItemCheckOutInfoDTO singleItemCheckOutInfoDTO) throws FriendlyException, UnsupportedEncodingException {
        String paymentUrl = paymentService.singleItemCheckOutUnAuthenticate(singleItemCheckOutInfoDTO);
        ResponseDTO<String> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, paymentUrl, "output");
        return ResponseEntity.ok(result);
    }
}
