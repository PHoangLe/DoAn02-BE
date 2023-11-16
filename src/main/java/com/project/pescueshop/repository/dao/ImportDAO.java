package com.project.pescueshop.repository.dao;

import com.project.pescueshop.model.dto.CartItemDTO;
import com.project.pescueshop.model.dto.ImportItemListDTO;
import com.project.pescueshop.model.entity.ImportInvoice;
import com.project.pescueshop.model.entity.ImportItem;
import com.project.pescueshop.repository.ImportInvoiceRepository;
import com.project.pescueshop.repository.ImportItemRepository;
import com.project.pescueshop.repository.mapper.ImportItemListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ImportDAO extends BaseDAO{
    private final ImportInvoiceRepository importInvoiceRepository;
    private final ImportItemRepository importItemRepository;
    private final ImportItemListMapper importItemListMapper;

    public void saveAndFlushInvoice(ImportInvoice importInvoice){
        importInvoiceRepository.saveAndFlush(importInvoice);
    }

    public void saveAndFlushItem(ImportItem importItem){
        importItemRepository.saveAndFlush(importItem);
    }

    public ImportInvoice findImportInvoiceById(String invoiceId){
        return importInvoiceRepository.findById(invoiceId).orElse(null);
    }

    public ImportItem findImportItemByVarietyIdAndInvoiceId(String varietyId, String importInvoiceId) {
        return importItemRepository.findImportItemByVarietyIdAndInvoiceId(varietyId, importInvoiceId).orElse(null);
    }

    public void deleteImportItem(ImportItem importItem) {
        importItemRepository.delete(importItem);
    }

    public List<ImportItemListDTO> getImportItemsByInvoiceId(String invoiceId){
        String sql = "SELECT * FROM get_import_item_list(:p_import_invoice_id);";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("p_import_invoice_id", invoiceId);

        return jdbcTemplate.query(sql, parameters, importItemListMapper);
    }

    public List<ImportInvoice> findAllImportInvoice(){
        return importInvoiceRepository.findAll();
    }
}
