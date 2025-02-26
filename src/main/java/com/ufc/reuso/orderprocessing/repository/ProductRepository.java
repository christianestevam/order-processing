package com.ufc.reuso.orderprocessing.repository;

import com.ufc.reuso.orderprocessing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}