package com.ufc.reuso.orderprocessing.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponseDTO {
    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
}