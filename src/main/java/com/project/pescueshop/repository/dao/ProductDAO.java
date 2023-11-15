package com.project.pescueshop.repository.dao;

import com.project.pescueshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDAO extends BaseDAO{
    private final ProductRepository productRepository;

    public void deleteAttribute(String productId, String attributeId){
        String sql = "SELECT delete_product_attribute(:p_productId, :p_attributeId);";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_productId", productId)
                .addValue("p_attributeId", attributeId);

        jdbcTemplate.update(sql, parameters);
    }

    public String getProductImage(String productId){
        String sql = "SELECT images FROM products_images WHERE product_product_id = ?";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_productId", productId);

        return jdbcTemplate.queryForObject(sql, parameters, String.class);
    }
}
