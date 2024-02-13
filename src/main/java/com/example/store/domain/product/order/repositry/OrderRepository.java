package com.example.store.domain.product.order.repositry;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyerOrderByIdDesc(Member buyer);
}
