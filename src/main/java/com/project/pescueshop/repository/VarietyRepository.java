package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.Variety;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarietyRepository extends JpaRepository<Variety, String> {
}
