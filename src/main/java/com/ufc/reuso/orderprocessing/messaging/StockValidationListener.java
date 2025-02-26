package com.ufc.reuso.orderprocessing.messaging;

import com.ufc.reuso.orderprocessing.events.StockValidatedEvent;
import com.ufc.reuso.orderprocessing.model.Product;
import com.ufc.reuso.orderprocessing.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockValidationListener {

    private final ProductRepository productRepository;
    private final EventPublisher eventPublisher;

    public StockValidationListener(ProductRepository productRepository, EventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "order.stock.validated")
    public void handleStockValidation(String orderIdStr) {
        UUID orderId = UUID.fromString(orderIdStr); // Conversão para UUID

        boolean stockAvailable = productRepository.count() > 0;

        System.out.println("Validando estoque para o pedido: " + orderId);

        StockValidatedEvent event = new StockValidatedEvent(orderId, stockAvailable);
        eventPublisher.publishEvent("order.stock.validation.response", event);
    }
}
