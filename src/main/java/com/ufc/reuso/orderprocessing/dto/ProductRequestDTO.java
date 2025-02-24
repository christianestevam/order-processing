package com.ufc.reuso.orderprocessing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequestDTO {
    @NotNull
    private String name;
    
    @NotNull
    private BigDecimal price;
    
    @NotNull
    private Integer stockQuantity;
}