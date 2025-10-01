package com.ecommerceapi.ecommerce_api.repository;

import com.ecommerceapi.ecommerce_api.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
