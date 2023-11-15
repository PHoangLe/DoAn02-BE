package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.dto.UpdateCartItemDTO;
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

    public void updateCartItem(UpdateCartItemDTO dto, User user) throws FriendlyException {
        Variety variety = varietyService.findById(dto.getVarietyId());

        if (variety == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        Cart cart = findCartByUserId(user.getUserId());
        CartItem cartItem = cartDAO.findByVarietyId(dto.getVarietyId());

        if (cartItem != null && dto.getQuantity() == 0){
            cartItemRepository.delete(cartItem);
            return;
        }

        if (cart == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        cartItem.setCartId(cart.getCartId());
        cartItem.setProduct(variety);
        cartItem.setSelected(true);
        cartItem.setQuantity(dto.getQuantity());
        cartItem.setTotalItemPrice(dto.getQuantity() * variety.getPrice());

        cartItemRepository.saveAndFlush(cartItem);
    }
}
