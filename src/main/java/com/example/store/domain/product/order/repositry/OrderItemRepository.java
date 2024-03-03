package com.example.store.domain.product.order.repositry;

import com.example.store.domain.product.order.entity.OrderItem;
import com.example.store.domain.rebate.rebate.entity.RebateItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderPayDateBetweenAndOrderRefundDateAndRebateItemOrderByIdDesc(
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime refundDate,
            RebateItem rebateItem
    );

    Page<OrderItem> findByOrderPayDateBetweenAndOrderRefundDateAndRebateItem(
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime refundDate,
            RebateItem rebateItem,
            Pageable pageable
    );
}
