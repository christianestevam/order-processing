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

// OrderPaymentController.java
package com.ufc.reuso.orderprocessing.controller;

import com.ufc.reuso.orderprocessing.dto.OrderPaymentRequestDTO;
import com.ufc.reuso.orderprocessing.dto.OrderPaymentResponseDTO;
import com.ufc.reuso.orderprocessing.model.OrderPayment;
import com.ufc.reuso.orderprocessing.model.PaymentStatus;
import com.ufc.reuso.orderprocessing.repository.OrderPaymentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class OrderPaymentController {
    
    @Autowired
    private OrderPaymentRepository repository;

    @PostMapping
    public ResponseEntity<OrderPaymentResponseDTO> createPayment(@Valid @RequestBody OrderPaymentRequestDTO dto) {
        OrderPayment payment = new OrderPayment();
        payment.setOrderId(dto.getOrderId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        
        OrderPayment savedPayment = repository.save(payment);
        return ResponseEntity.ok(toResponseDTO(savedPayment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderPaymentResponseDTO> getPaymentById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(payment -> ResponseEntity.ok(toResponseDTO(payment)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderPaymentResponseDTO>> getPaymentsByOrderId(@PathVariable UUID orderId) {
        List<OrderPaymentResponseDTO> payments = repository.findByOrderId(orderId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(payments);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<OrderPaymentResponseDTO> confirmPayment(@PathVariable UUID id, @RequestParam String transactionId) {
        return repository.findById(id).map(payment -> {
            payment.markAsPaid(transactionId);
            repository.save(payment);
            return ResponseEntity.ok(toResponseDTO(payment));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<OrderPaymentResponseDTO> failPayment(@PathVariable UUID id) {
        return repository.findById(id).map(payment -> {
            payment.markAsFailed();
            repository.save(payment);
            return ResponseEntity.ok(toResponseDTO(payment));
        }).orElse(ResponseEntity.notFound().build());
    }

    private OrderPaymentResponseDTO toResponseDTO(OrderPayment payment) {
        OrderPaymentResponseDTO dto = new OrderPaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrderId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setTransactionId(payment.getTransactionId());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }
}