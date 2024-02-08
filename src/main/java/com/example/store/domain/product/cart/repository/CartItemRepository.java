package com.example.store.domain.product.cart.repository;

import com.example.store.domain.product.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
