package com.ufc.reuso.orderprocessing.controller;

import com.ufc.reuso.orderprocessing.dto.InvoiceRequestDTO;
import com.ufc.reuso.orderprocessing.dto.InvoiceResponseDTO;
import com.ufc.reuso.orderprocessing.model.Invoice;
import com.ufc.reuso.orderprocessing.repository.InvoiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    
    @Autowired
    private InvoiceRepository repository;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@Valid @RequestBody InvoiceRequestDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setOrderId(dto.getOrderId());
        invoice.setInvoiceNumber(UUID.randomUUID().toString());
        
        Invoice savedInvoice = repository.save(invoice);
        return ResponseEntity.ok(toResponseDTO(savedInvoice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(invoice -> ResponseEntity.ok(toResponseDTO(invoice)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoicesByOrderId(@PathVariable UUID orderId) {
        List<InvoiceResponseDTO> invoices = repository.findByOrderId(orderId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invoices);
    }

    private InvoiceResponseDTO toResponseDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setOrderId(invoice.getOrderId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setInvoicePdfUrl(invoice.getInvoicePdfUrl());
        dto.setIssuedAt(invoice.getIssuedAt());
        return dto;
    }
}
