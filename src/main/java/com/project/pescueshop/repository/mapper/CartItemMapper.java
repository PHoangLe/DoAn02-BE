package com.project.pescueshop.repository.mapper;

import com.project.pescueshop.model.dto.CartItemDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CartItemMapper implements RowMapper<CartItemDTO> {
    @Override
    public CartItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        return CartItemDTO.builder()
                .userId(rs.getString("user_id"))
                .cartId(rs.getString("cart_id"))
                .cartItemId(rs.getString("cart_item_id"))
                .isSelected(rs.getBoolean("is_selected"))
                .quantity(rs.getInt("quantity"))
                .totalItemPrice(rs.getLong("total_item_price"))
                .varietyId(rs.getString("variety_id"))
                .name(rs.getString("name"))
                .unitPrice(rs.getLong("unit_price"))
                .image(rs.getString("image"))
                .stockAmount(rs.getInt("stock_amount"))
                .build();
    }
}
