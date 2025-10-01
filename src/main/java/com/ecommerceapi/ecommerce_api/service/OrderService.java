package com.ecommerceapi.ecommerce_api.service;

import com.ecommerceapi.ecommerce_api.model.*;
import com.ecommerceapi.ecommerce_api.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    public Order checkout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findAll()
                .stream()
                .filter(item -> item.getCart().getId().equals(cart.getId()))
                .toList();

        if (cartItems.isEmpty()) {
           logger.info("Cart is empty");
        }

        // Calculate total price
        double total = cartItems.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(total);
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        // Create order items
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            orderItemRepository.save(oi);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return order;
    }

    public Order markAsPaid(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("PAID");
        return orderRepository.save(order);
    }
}
