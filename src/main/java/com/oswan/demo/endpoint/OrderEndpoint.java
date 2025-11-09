package com.oswan.demo.endpoint;

import com.oswan.demo.order.CreateOrderRequest;
import com.oswan.demo.order.CreateOrderResponse;
import com.oswan.demo.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class OrderEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/orders";
    private final OrderService orderService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateOrderRequest")
    @ResponsePayload
    public CreateOrderResponse createOrder(@RequestPayload CreateOrderRequest request) {
        return orderService.createOrder(request.getCustomerName(), request.getTotalAmount());
    }
}
