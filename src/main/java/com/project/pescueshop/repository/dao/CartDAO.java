package com.project.pescueshop.repository.dao;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.entity.CartItem;
import com.project.pescueshop.repository.CartItemRepository;
import com.project.pescueshop.repository.mapper.CartItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CartDAO extends BaseDAO{
    private final CartItemMapper cartItemMapper;
    private final CartItemRepository cartItemRepository;

    public List<CartItemDTO> getCartItemsByUserId(String userId){
        String sql = "SELECT * FROM get_cart_by_user_id(:p_user_id);";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_user_id", userId);

        return jdbcTemplate.query(sql, parameters, cartItemMapper);
    }

    public CartItem findById(String cartItemId){
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    public CartItem findByVarietyId(String varietyId){
        return cartItemRepository.findByVarietyId(varietyId).orElse(new CartItem());
    }
}
