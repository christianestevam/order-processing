package com.ufc.reuso.orderprocessing.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus;

    public void updateStock(int quantity) {
        this.stockQuantity -= quantity;
        if (this.stockQuantity <= 0) {
            this.stockStatus = StockStatus.OUT_OF_STOCK;
        }
    }
}