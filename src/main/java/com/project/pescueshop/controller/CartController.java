package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.Cart;
import com.project.pescueshop.model.entity.CartItem;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.AuthenticationService;
import com.project.pescueshop.service.CartService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class CartController {
    private final CartService cartService;
    private final AuthenticationService authenticationService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<Cart>> getCart() throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        Cart cart = cartService.findCartByUserId(user.getUserId());

        ResponseDTO<Cart> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, cart);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO<CartItem>> addItemToCart(@RequestBody CartItemDTO dto) throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        CartItem cart = cartService.addItemToCart(dto, user);

        ResponseDTO<CartItem> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, cart);
        return ResponseEntity.ok(result);
    }
}
