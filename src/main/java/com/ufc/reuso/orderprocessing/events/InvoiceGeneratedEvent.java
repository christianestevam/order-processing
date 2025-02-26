package com.ufc.reuso.orderprocessing.events;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceGeneratedEvent {
    private UUID orderId;
    private boolean isInvoiceGenerated;
    private String invoiceNumber;
}
