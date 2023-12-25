package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.InvoiceItemDTO;
import com.project.pescueshop.model.dto.InvoiceListResultDTO;
import com.project.pescueshop.model.entity.Invoice;
import com.project.pescueshop.model.entity.User;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.dao.PaymentDAO;
import com.project.pescueshop.util.constant.EnumInvoiceStatus;
import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final PaymentDAO paymentDAO;

    public List<InvoiceListResultDTO> findAllInvoice(Date fromDate, Date toDate){
        if (fromDate == null && toDate == null){
            return paymentDAO.findAllInvoice();
        }

        return paymentDAO.findAllInvoice().stream()
                .filter(invoice -> invoice.getInvoice().getCreatedDate().before(toDate) && invoice.getInvoice().getCreatedDate().after(fromDate))
                .toList();
    }

    public List<InvoiceItemDTO> getInvoiceDetail(String invoiceId){
        return paymentDAO.getInvoiceDetail(invoiceId);
    }

    public Invoice updateInvoiceStatus(Invoice invoice,EnumInvoiceStatus status){
        invoice.setStatus(status.getValue());
        paymentDAO.saveAndFlushInvoice(invoice);
        return invoice;
    }

    public Invoice updateInvoiceStatus(String invoiceId, String status) throws FriendlyException {
        EnumInvoiceStatus enumInvoiceStatus = EnumInvoiceStatus.getByValue(status);
        Invoice invoice = paymentDAO.findInvoiceById(invoiceId);

        if (invoice == null){
            throw new FriendlyException(EnumResponseCode.INVOICE_NOT_FOUND);
        }

        return updateInvoiceStatus(invoice, enumInvoiceStatus);
    }

    public List<Invoice> getOrderInfoByUser(User user) {
        return paymentDAO.findAllInvoiceByUserId(user.getUserId());
    }
}
