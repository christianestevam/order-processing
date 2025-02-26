package com.ufc.reuso.orderprocessing.messaging;

import com.ufc.reuso.orderprocessing.events.InvoiceGeneratedEvent;
import com.ufc.reuso.orderprocessing.model.Invoice;
import com.ufc.reuso.orderprocessing.repository.InvoiceRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceGenerationListener {

    private final InvoiceRepository invoiceRepository;
    private final EventPublisher eventPublisher;

    public InvoiceGenerationListener(InvoiceRepository invoiceRepository, EventPublisher eventPublisher) {
        this.invoiceRepository = invoiceRepository;
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "order.invoice.generation")
    public void handleInvoiceGeneration(String orderIdStr) {
        UUID orderId = UUID.fromString(orderIdStr); // Conversão para UUID

        String invoiceNumber = "INV-" + UUID.randomUUID();
        Invoice invoice = new Invoice(orderId, invoiceNumber, "http://invoice-url.com/" + invoiceNumber);
        invoiceRepository.save(invoice);

        System.out.println("Nota fiscal gerada para o pedido: " + orderId);

        InvoiceGeneratedEvent event = new InvoiceGeneratedEvent(orderId, true, invoiceNumber);
        eventPublisher.publishEvent("order.invoice.generated", event);
    }
}
