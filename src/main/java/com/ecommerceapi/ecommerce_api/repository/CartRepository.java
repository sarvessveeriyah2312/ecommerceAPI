package com.ecommerceapi.ecommerce_api.repository;

import com.ecommerceapi.ecommerce_api.model.Cart;
import com.ecommerceapi.ecommerce_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
