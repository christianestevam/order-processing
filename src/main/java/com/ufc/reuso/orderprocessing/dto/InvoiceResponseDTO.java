package com.ufc.reuso.orderprocessing.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class InvoiceResponseDTO {
    private UUID id;
    private UUID orderId;
    private String invoiceNumber;
    private String invoicePdfUrl;
    private LocalDateTime issuedAt;
}