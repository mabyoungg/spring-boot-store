package com.example.store.domain.product.order.repositry;

import com.example.store.domain.product.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
