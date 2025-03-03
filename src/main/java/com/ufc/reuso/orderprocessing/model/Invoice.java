package com.ufc.reuso.orderprocessing.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    private String invoicePdfUrl;

    private LocalDateTime issuedAt;

    @PrePersist
    protected void onCreate() {
        issuedAt = LocalDateTime.now();
    }

    public Invoice(UUID orderId, String invoiceNumber, String invoicePdfUrl) {
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.invoicePdfUrl = invoicePdfUrl;
        this.issuedAt = LocalDateTime.now();
    }
}
