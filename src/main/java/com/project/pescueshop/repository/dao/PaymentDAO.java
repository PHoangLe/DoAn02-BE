package com.project.pescueshop.repository.dao;

import com.project.pescueshop.model.dto.InvoiceItemDTO;
import com.project.pescueshop.model.entity.ImportInvoice;
import com.project.pescueshop.model.entity.ImportItem;
import com.project.pescueshop.model.entity.Invoice;
import com.project.pescueshop.model.entity.InvoiceItem;
import com.project.pescueshop.repository.InvoiceItemRepository;
import com.project.pescueshop.repository.InvoiceRepository;
import com.project.pescueshop.repository.mapper.InvoiceItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PaymentDAO extends BaseDAO{
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceItemMapper invoiceItemMapper;

    public void saveAndFlushInvoice(Invoice invoice){
        invoiceRepository.saveAndFlush(invoice);
    }

    public void saveAndFlushItem(InvoiceItem item){
        invoiceItemRepository.saveAndFlush(item);
    }

    public Invoice findInvoiceById(String invoiceId){
        return invoiceRepository.findById(invoiceId).orElse(null);
    }

    public List<Invoice> findInvoiceByDateRange(Date fromDate, Date toDate){
        return invoiceRepository.findInvoiceByDateRange(fromDate, toDate);
    }

    public List<InvoiceItemDTO> getInvoiceDetail(String invoiceId){
        String sql = "SELECT * FROM get_invoice_detail(:p_invoice_id);";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_invoice_id", invoiceId);

        return jdbcTemplate.query(sql, parameters, invoiceItemMapper);
    }

    public List<Invoice> findAllInvoice(){
        return invoiceRepository.findAll();
    }

    public List<Invoice> findAllInvoiceByUserId(String userId) {
        return invoiceRepository.findAllInvoiceByUserId(userId);
    }
}
