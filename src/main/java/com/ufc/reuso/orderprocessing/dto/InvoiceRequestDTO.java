package com.ufc.reuso.orderprocessing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class InvoiceRequestDTO {
    @NotNull
    private UUID orderId;
}
