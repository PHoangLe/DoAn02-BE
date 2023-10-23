package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.entity.VarietyAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE p.productId = ?1 AND p.status = 'ACTIVE'")
    Optional<Product> findByProductId(String id);
}
