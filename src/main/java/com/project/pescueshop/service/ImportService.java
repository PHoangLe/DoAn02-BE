package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.AddOrUpdateImportItemDTO;
import com.project.pescueshop.model.dto.ImportItemListDTO;
import com.project.pescueshop.model.entity.*;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.dao.ImportDAO;
import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class ImportService {
    private final ImportDAO importDAO;
    private final VarietyService varietyService;
    private final ThreadService threadService;

    public ImportInvoice addNewImportInvoice(User user, List<AddOrUpdateImportItemDTO> itemDTOList){
        ImportInvoice invoice = ImportInvoice.builder()
                .createdDate(new Date())
                .userId(user.getUserId())
                .build();

        Long totalPrice = itemDTOList.stream()
                        .mapToLong(item -> item.getImportPrice() * item.getQuantity())
                        .sum();

        invoice.setTotalPrice(totalPrice);

        importDAO.saveAndFlushInvoice(invoice);

        CompletableFuture.runAsync(() -> {
            threadService.addImportItemToInvoice(invoice, itemDTOList);
        });

        return invoice;
    }

    public ImportInvoice getImportInvoiceById(String importInvoiceId){
        return importDAO.findImportInvoiceById(importInvoiceId);
    }

    public List<ImportInvoice> getAllImportInvoice(){
        return importDAO.findAllImportInvoice();
    }

    public void addOrUpdateImportItem(AddOrUpdateImportItemDTO dto) throws FriendlyException {
        Variety variety = varietyService.findById(dto.getVarietyId());

        if (variety == null){
            throw new FriendlyException(EnumResponseCode.VARIETY_NOT_FOUND);
        }

        ImportInvoice invoice = getImportInvoiceById(dto.getImportInvoiceId());
        ImportItem importItem = importDAO.findImportItemByVarietyIdAndInvoiceId(dto.getVarietyId(), dto.getImportInvoiceId());

        if (invoice == null){
            throw new FriendlyException(EnumResponseCode.IMPORT_INVOICE_NOT_FOUND);
        }

        if (importItem != null && dto.getQuantity() == 0){
            importDAO.deleteImportItem(importItem);
            return;
        }

        if (importItem == null){
            importItem = new ImportItem();
        }
        importItem.setImportInvoiceId(invoice.getImportInvoiceId());
        importItem.setVariety(variety);
        importItem.setQuantity(dto.getQuantity());
        importItem.setImportPrice(dto.getImportPrice());
        importItem.setTotalImportPrice(dto.getQuantity() * dto.getImportPrice());

        importDAO.saveAndFlushItem(importItem);
    }

    public List<ImportItemListDTO> getImportItemListByInvoiceId(String invoiceId){
        return importDAO.getImportItemsByInvoiceId(invoiceId);
    }
}
