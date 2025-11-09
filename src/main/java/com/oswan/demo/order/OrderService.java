package com.oswan.demo.order;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Transactional
    public CreateOrderResponse createOrder(String customerName, double totalAmount) {
        Order order = Order.builder()
            .customerName(customerName).totalAmount(totalAmount).status("PENDING").build();
        order = orderRepository.save(order);
        kafkaTemplate.send("order-created", order.getId().toString());
        CreateOrderResponse response = new CreateOrderResponse();
        response.setOrderId(order.getId());
        return response;
    }

    @Transactional
    protected void updateStatus(Long id, String status){
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

}
