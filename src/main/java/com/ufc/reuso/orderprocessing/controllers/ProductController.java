package com.ufc.reuso.orderprocessing.controller;

import com.ufc.reuso.orderprocessing.dto.ProductRequestDTO;
import com.ufc.reuso.orderprocessing.dto.ProductResponseDTO;
import com.ufc.reuso.orderprocessing.model.Product;
import com.ufc.reuso.orderprocessing.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductRepository repository;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        
        Product savedProduct = repository.save(product);
        return ResponseEntity.ok(toResponseDTO(savedProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(product -> ResponseEntity.ok(toResponseDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        return dto;
    }
}