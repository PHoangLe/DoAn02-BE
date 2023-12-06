package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.InvoiceItemDTO;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.Invoice;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.InvoiceService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoice")
@CrossOrigin
@RequiredArgsConstructor
@Api
public class InvoiceController {
    private final InvoiceService invoiceService;

    @GetMapping("/{invoiceId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<List<InvoiceItemDTO>>> getInvoiceDetail(@PathVariable String invoiceId){
        List<InvoiceItemDTO> invoiceItemDTOS = invoiceService.getInvoiceDetail(invoiceId);
        ResponseDTO<List<InvoiceItemDTO>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, invoiceItemDTOS, "invoiceItemList");
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<List<Invoice>>> getAllInvoice(){
        List<Invoice> invoice = invoiceService.findAllInvoice();
        ResponseDTO<List<Invoice>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, invoice, "invoiceList");
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ResponseDTO<Invoice>> getAllInvoice(
            @RequestParam String invoiceId,
            @RequestParam String status
    ) throws FriendlyException {
        Invoice invoice = invoiceService.updateInvoiceStatus(invoiceId, status);
        ResponseDTO<Invoice> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, invoice, "invoice");
        return ResponseEntity.ok(result);
    }
}