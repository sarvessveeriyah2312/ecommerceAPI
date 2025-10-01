package com.ecommerceapi.ecommerce_api.repository;

import com.ecommerceapi.ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
