package com.ufc.reuso.orderprocessing.messaging;

import com.ufc.reuso.orderprocessing.events.PaymentProcessedEvent;
import com.ufc.reuso.orderprocessing.dto.OrderPaymentRequestDTO;
import com.ufc.reuso.orderprocessing.model.OrderPayment;
import com.ufc.reuso.orderprocessing.repository.OrderPaymentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentProcessingListener {

    private final OrderPaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    public PaymentProcessingListener(OrderPaymentRepository paymentRepository, EventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    @RabbitListener(queues = "order.payment.processing")
    public void handlePaymentProcessing(OrderPaymentRequestDTO paymentRequest) {
        Optional<OrderPayment> optionalPayment = paymentRepository
                .findByOrderId(paymentRequest.getOrderId())
                .stream().findFirst();

        if (optionalPayment.isPresent()) {
            OrderPayment payment = optionalPayment.get();
            payment.markAsPaid(UUID.randomUUID().toString());
            paymentRepository.save(payment);

            System.out.println("Pagamento processado para o pedido: " + paymentRequest.getOrderId());

            PaymentProcessedEvent event = new PaymentProcessedEvent(paymentRequest.getOrderId(), true);
            eventPublisher.publishEvent("order.payment.processed", event);
        } else {
            System.out.println("Falha ao processar pagamento do pedido: " + paymentRequest.getOrderId());
            eventPublisher.publishEvent("order.payment.processed", new PaymentProcessedEvent(paymentRequest.getOrderId(), false));
        }
    }
}
