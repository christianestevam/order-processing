package com.ufc.reuso.orderprocessing.dto;

import com.ufc.reuso.orderprocessing.model.PaymentMethod;
import com.ufc.reuso.orderprocessing.model.PaymentStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderPaymentResponseDTO {
    private UUID id;
    private UUID orderId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime paymentDate;
}
