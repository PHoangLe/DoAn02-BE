package com.project.pescueshop.service;

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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService{
    private final CartRepository cartRepository;
    private final CartDAO cartDAO;
    private final CartItemRepository cartItemRepository;
    private final VarietyService varietyService;
    private final ProductService productService;

    public void createCartForNewUser(String userId){
        Cart newCart = Cart.builder()
                .userId(userId)
                .build();

        cartRepository.saveAndFlush(newCart);
    }

    public Cart findCartByUserId(String userId){
        return cartRepository.getCartByUserId(userId);
    }

    public List<CartItemDTO> getCartItemByUserId(String userId){
        return cartDAO.getCartItemsByUserId(userId);
    }

    public void addOrUpdateCartItem(AddOrUpdateCartItemDTO dto, User user) throws FriendlyException {
        Variety variety = varietyService.findById(dto.getVarietyId());

        if (variety == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        Cart cart = findCartByUserId(user.getUserId());
        if (cart == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        CartItem cartItem = cartDAO.findByVarietyIdAndCartId(dto.getVarietyId(), cart.getCartId());
        int newQuantity = cartItem == null ? dto.getQuantity() : cartItem.getQuantity() + dto.getQuantity();
        if (cartItem != null && newQuantity == 0){
            cartItemRepository.delete(cartItem);
            cartItemRepository.saveAndFlush(cartItem);
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
}
