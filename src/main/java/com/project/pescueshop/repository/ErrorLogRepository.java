package com.project.pescueshop.repository;

import com.project.pescueshop.model.general.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Integer> {
}
