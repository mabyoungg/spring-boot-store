package com.example.store.domain.product.order.repositry;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<Order> search(Member buyer, Boolean payStatus, Boolean cancelStatus, Boolean refundStatus, Pageable pageable);
}
