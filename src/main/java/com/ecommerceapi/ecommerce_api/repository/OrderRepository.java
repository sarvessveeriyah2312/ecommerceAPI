package com.ecommerceapi.ecommerce_api.repository;

import com.ecommerceapi.ecommerce_api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
