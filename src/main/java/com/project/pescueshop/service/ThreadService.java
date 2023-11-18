package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.AddOrUpdateImportItemDTO;
import com.project.pescueshop.model.entity.*;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.util.constant.EnumStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ThreadService extends BaseService {
    private final VarietyService varietyService;
    private final ImportService importService;
    @Autowired
    public ThreadService(@Lazy VarietyService varietyService,@Lazy ImportService importService) {
        this.varietyService = varietyService;
        this.importService = importService;
    }

    public void addVarietyByAttribute(Product product, List<VarietyAttribute> existingAttributes, VarietyAttribute newAttribute) {
        if (existingAttributes == null || existingAttributes.isEmpty()) {
            return;
        }

        for (VarietyAttribute varietyAttribute : existingAttributes) {
            try {
                Thread thread = new Thread(() -> processAddVarietyByAttribute(product, varietyAttribute, newAttribute));
                thread.start();
            }
            catch (Exception e){
                log.trace(e.getMessage());
                log.trace("Product Id:" + product.getProductId());
                log.trace("Attribute 1:" + varietyAttribute.getAttributeId());
                log.trace("Attribute 2:" + newAttribute.getAttributeId());
            }
        }
    }

    public void addVarietyByAttribute(Product product, List<VarietyAttribute> colorAttributeList, List<VarietyAttribute> sizeAttributeList) {
        if (colorAttributeList == null || colorAttributeList.isEmpty()) {
            return;
        }

        for (VarietyAttribute colorAttribute : colorAttributeList) {
            Thread thread = new Thread(() -> addVarietyByAttribute(product, sizeAttributeList, colorAttribute));
            thread.start();
        }
    }

    private void processAddVarietyByAttribute(Product product, VarietyAttribute existingAttribute, VarietyAttribute newAttribute) {
        Variety variety = new Variety();
        variety.addAttribute(newAttribute);
        variety.setName(product.getName());
        variety.addAttribute(existingAttribute);
        variety.setProductId(product.getProductId());
        variety.setStatus(EnumStatus.ACTIVE.getValue());
        variety.setPrice(product.getPrice());
        variety.setStockAmount(0);
        varietyService.addOrUpdateVariety(variety);
    }

    public void addImportItemToInvoice(ImportInvoice invoice, List<AddOrUpdateImportItemDTO> itemDTOList) {
        for (AddOrUpdateImportItemDTO dto : itemDTOList) {
            Thread thread = new Thread(() -> {
                try {
                    processAddOrUpdateImportItem(invoice, dto);
                } catch (FriendlyException e) {
                    log.trace(e.getMessage());
                    log.trace("Product ID:" + invoice.getImportInvoiceId());
                    log.trace("Variety ID:" + dto.getVarietyId());
                }
            });
            thread.start();
        }
    }

    public void processAddOrUpdateImportItem(ImportInvoice invoice, AddOrUpdateImportItemDTO dto) throws FriendlyException{
        importService.addOrUpdateImportItem(invoice, dto);
    }
}
