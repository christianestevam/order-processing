package com.ufc.reuso.orderprocessing.repository;

import com.ufc.reuso.orderprocessing.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findByOrderId(UUID orderId);
}