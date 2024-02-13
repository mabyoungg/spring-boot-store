package com.example.store.domain.product.cart.repository;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.cart.entity.CartItem;
import com.example.store.domain.product.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuyer(Member buyer);

    boolean existsByBuyerAndProduct(Member buyer, Product product);

    void deleteByBuyerAndProduct(Member buyer, Product product);
}
