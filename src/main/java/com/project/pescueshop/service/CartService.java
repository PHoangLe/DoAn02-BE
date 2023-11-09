package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.entity.Cart;
import com.project.pescueshop.model.entity.CartItem;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.CartItemRepository;
import com.project.pescueshop.repository.CartRepository;
import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final VarietyService varietyService;

    public void createCartForNewUser(String userId){
        Cart newCart = Cart.builder()
                .userId(userId)
                .build();

        cartRepository.saveAndFlush(newCart);
    }

    public Cart findCartByUserId(String userId){
        return cartRepository.getCartByUserId(userId);
    }

    public CartItem addItemToCart(CartItemDTO dto, User user) throws FriendlyException {
        Variety variety = varietyService.findById(dto.getVarietyId());

        if (variety == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        Cart cart = findCartByUserId(user.getUserId());

        if (cart == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        CartItem cartItem = new CartItem();
        cartItem.setCartId(cart.getCartId());
        cartItem.setProduct(variety);
        cartItem.setSelected(true);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setTotalItemPrice(dto.getQuantity() * variety.getPrice());

        cartItemRepository.saveAndFlush(cartItem);

        return cartItem;
    }
}
