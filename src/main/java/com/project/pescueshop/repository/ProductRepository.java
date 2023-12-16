package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p FROM Product p WHERE p.productId = ?1 AND p.status = 'ACTIVE'")
    Optional<Product> findByProductId(String id);

    @Query(value = "SELECT * FROM Product p WHERE p.brand_id = ?1 AND p.status = 'ACTIVE'", nativeQuery = true)
    List<Product> getProductByBrandId(String brandId);

    @Query(value = "SELECT * FROM Product p WHERE p.category_id = ?1 AND p.status = 'ACTIVE'", nativeQuery = true)
    List<Product> getProductByCategoryId(String categoryId);

    @Query(value = "SELECT p " +
            "FROM Product p " +
            "JOIN ViewAuditLog val ON p.productId = val.objectId " +
            "WHERE val.objectType = 'PRODUCT' " +
            "GROUP BY p.productId " +
            "ORDER BY COUNT(val.viewAuditLogId) DESC")
    List<Product> getMostViewsProducts(Pageable pageable);

    @Query(value = "SELECT p" +
            "    FROM Product p" +
            "    ORDER BY RANDOM() ")
    List<Product> getRandomNProduct(Pageable pageable);
}
