package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.CartDTO;
import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.dto.AddOrUpdateCartItemDTO;
import com.project.pescueshop.model.entity.*;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.CartItemRepository;
import com.project.pescueshop.repository.CartRepository;
import com.project.pescueshop.repository.dao.CartDAO;
import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService{
    private final CartRepository cartRepository;
    private final CartDAO cartDAO;
    private final CartItemRepository cartItemRepository;
    private final VarietyService varietyService;
    private final UserService userService;

    public String createCartForNewUser(String userId){
        Cart newCart = Cart.builder()
                .userId(userId)
                .build();

        cartRepository.saveAndFlush(newCart);
        return newCart.getCartId();
    }

    public Cart findCartByUserId(String userId){
        return cartRepository.getCartByUserId(userId);
    }

    public Cart findCartByCartId(String cartId){
        return cartRepository.findById(cartId).orElse(null);
    }

    public List<CartItemDTO> getCartItemByUserId(String userId){
        return cartDAO.getCartItems(userId, null);
    }

    public List<CartItemDTO> getCartItemByCartId(String cartId) {
        return cartDAO.getCartItems(null, cartId);
    }

    public void addOrUpdateCartItem(AddOrUpdateCartItemDTO dto, User user, Cart existedCart) throws FriendlyException {
        Variety variety = varietyService.findById(dto.getVarietyId());

        if (variety == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        Cart cart = existedCart != null ? existedCart : findCartByUserId(user.getUserId());
        if (cart == null){
            throw new FriendlyException(EnumResponseCode.CART_NOT_FOUND);
        }

        CartItem cartItem = cartDAO.findByVarietyIdAndCartId(dto.getVarietyId(), cart.getCartId());
        int newQuantity = cartItem == null ? dto.getQuantity() : cartItem.getQuantity() + dto.getQuantity();
        if (cartItem != null && newQuantity == 0){
            cart.getCartItemList().remove(cartItem);
            cartRepository.saveAndFlush(cart);
            return;
        } else if (newQuantity < 0) {
            return;
        }

        if (cartItem == null) {
            cartItem = new CartItem();
        }
        cartItem.setCartId(cart.getCartId());
        cartItem.setProduct(variety);
        cartItem.setSelected(true);
        cartItem.setQuantity(newQuantity);
        cartItem.setTotalItemPrice(newQuantity * variety.getPrice());

        cartItemRepository.saveAndFlush(cartItem);
    }

    public void selectCartItem(String cartItemId) throws FriendlyException {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if (cartItem == null){
            throw new FriendlyException(EnumResponseCode.CART_ITEM_NOT_FOUND);
        }

        cartItem.setSelected(!cartItem.isSelected());

        cartItemRepository.saveAndFlush(cartItem);
    }

    public CartDTO getUnAuthenticatedCart(String cartId) {
        if (cartId != null) {
            List<CartItemDTO> cartItemDTOS = cartDAO.getCartItems(null, cartId);

            CartDTO cartDTO = CartDTO.builder()
                    .cartItemList(cartItemDTOS)
                    .build();

            if (!CollectionUtils.isEmpty(cartItemDTOS)){
                cartDTO.setCartId(cartItemDTOS.get(0).getCartId());
            }

            return cartDTO;
        }

        User user = userService.getAdminUser();
        String newCartId = createCartForNewUser(user.getUserId());

        return CartDTO.builder()
                .cartId(newCartId)
                .cartItemList(new ArrayList<>())
                .build();
    }

    public void addOrUpdateUnAuthenticatedCartItem(AddOrUpdateCartItemDTO dto, String cartId) throws FriendlyException {
        Cart cart = findCartByCartId(cartId);
        if (cart == null){
            throw new FriendlyException(EnumResponseCode.CART_NOT_FOUND);
        }

        User user = userService.getAdminUser();

        addOrUpdateCartItem(dto, user, cart);
    }
}
