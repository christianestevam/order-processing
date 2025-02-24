package com.ufc.reuso.orderprocessing.dto;

import com.ufc.reuso.orderprocessing.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderPaymentRequestDTO {
    @NotNull
    private UUID orderId;
    
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private PaymentMethod paymentMethod;
}