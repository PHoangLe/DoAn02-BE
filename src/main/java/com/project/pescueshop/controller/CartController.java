package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.dto.UpdateCartItemDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
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

import java.util.List;

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
    public ResponseEntity<ResponseDTO<List<CartItemDTO>>> getCart() throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        List<CartItemDTO> itemList = cartService.getCartItemByUserId(user.getUserId());
        ResponseDTO<List<CartItemDTO>> result;
        if (itemList == null) {
             result = new ResponseDTO<>(EnumResponseCode.CART_NOT_FOUND);
        }
        else {
            result = new ResponseDTO<>(EnumResponseCode.SUCCESS, itemList, "cartItems");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add-cart-item")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<CartItem>> addItemToCart(@RequestBody UpdateCartItemDTO dto) throws FriendlyException {
        User user = authenticationService.getCurrentLoggedInUser();
        cartService.updateCartItem(dto, user);

        ResponseDTO<CartItem> result = new ResponseDTO<>(EnumResponseCode.SUCCESS);
        return ResponseEntity.ok(result);
    }
}
