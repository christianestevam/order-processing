package com.ufc.reuso.orderprocessing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "order_payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime paymentDate;

    public void markAsPaid(String transactionId) {
        this.paymentStatus = PaymentStatus.CONFIRMED;
        this.transactionId = transactionId;
        this.paymentDate = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
    }
}