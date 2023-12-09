package com.project.pescueshop.repository;

import com.project.pescueshop.model.entity.Invoice;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    @Query("select i from Invoice i where i.userId = ?1 ")
    List<Invoice> findAllInvoiceByUserId(String userId);
}
