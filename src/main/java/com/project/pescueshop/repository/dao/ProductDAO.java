package com.project.pescueshop.repository.dao;

import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        String sql = "SELECT images FROM products_images WHERE product_product_id = :p_productId";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_productId", productId);

        return jdbcTemplate.queryForObject(sql, parameters, String.class);
    }

    public List<Product> getRandomNProduct(Integer n){
        n = n == null ? 5 : n;

        Pageable pageable = PageRequest.of(0, n);
        return productRepository.getRandomNProduct(pageable);
    }

    public List<Product> getMostViewsProducts(Integer n){
        n = n == null ? 5 : n;

        Pageable pageable = PageRequest.of(0, n);
        return productRepository.getMostViewsProducts(pageable);
    }

    public List<Product> getProductByBrandId(String brandId) {
        return productRepository.getProductByBrandId(brandId);
    }

    public List<Product> getProductByCategoryId(String categoryId) {
        return productRepository.getProductByCategoryId(categoryId);
    }

    public void saveAndFlushProduct(Product product){
        productRepository.saveAndFlush(product);
    }

    public Product findProductById(String id){
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
