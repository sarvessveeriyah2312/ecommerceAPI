package com.ecommerceapi.ecommerce_api.controller;

import com.ecommerceapi.ecommerce_api.model.Order;
import com.ecommerceapi.ecommerce_api.service.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Checkout -> convert cart to order
    @PostMapping("/checkout")
    public Order checkout(Authentication authentication) {
        String email = authentication.getName();
        return orderService.checkout(email);
    }

    // Mark order as paid (simulate payment success)
    @PostMapping("/{orderId}/pay")
    public Order payOrder(@PathVariable Long orderId) {
        return orderService.markAsPaid(orderId);
    }
}
