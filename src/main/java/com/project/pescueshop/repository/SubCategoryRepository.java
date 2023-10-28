package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, String> {
    public List<SubCategory> findSubCategoriesByCategoryId(String categoryId);
}
