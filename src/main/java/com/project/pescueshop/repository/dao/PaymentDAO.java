package com.project.pescueshop.repository.dao;

import com.project.pescueshop.model.entity.ImportInvoice;
import com.project.pescueshop.model.entity.ImportItem;
import com.project.pescueshop.model.entity.Invoice;
import com.project.pescueshop.model.entity.InvoiceItem;
import com.project.pescueshop.repository.InvoiceItemRepository;
import com.project.pescueshop.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentDAO extends BaseDAO{
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    public void saveAndFlushInvoice(Invoice invoice){
        invoiceRepository.saveAndFlush(invoice);
    }

    public void saveAndFlushItem(InvoiceItem item){
        invoiceItemRepository.saveAndFlush(item);
    }
}
