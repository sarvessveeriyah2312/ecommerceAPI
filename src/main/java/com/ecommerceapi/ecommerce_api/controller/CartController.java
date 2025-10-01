package com.ecommerceapi.ecommerce_api.controller;

import com.ecommerceapi.ecommerce_api.model.CartItem;
import com.ecommerceapi.ecommerce_api.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestParam Long productId,
                              @RequestParam int quantity,
                              Authentication authentication) {
        String email = authentication.getName();
        return cartService.addToCart(email, productId, quantity);
    }

    @GetMapping
    public List<CartItem> viewCart(Authentication authentication) {
        String email = authentication.getName();
        return cartService.viewCart(email);
    }

    @DeleteMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return "Item removed from cart!";
    }
}
