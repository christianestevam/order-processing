package com.ufc.reuso.orderprocessing.repository;

import com.ufc.reuso.orderprocessing.model.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface OrderPaymentRepository extends JpaRepository<OrderPayment, UUID> {
    List<OrderPayment> findByOrderId(UUID orderId);
}