package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.ImportInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportInvoiceRepository extends JpaRepository<ImportInvoice, String> {
}
