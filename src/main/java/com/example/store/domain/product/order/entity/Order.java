package com.example.store.domain.product.order.entity;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.cart.entity.CartItem;
import com.example.store.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
@Table(name = "order_")
public class Order extends BaseEntity {
    @ManyToOne
    private Member buyer;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime payDate;
    private LocalDateTime cancelDate;
    private LocalDateTime refundDate;

    public void addItem(CartItem cartItem) {
        OrderItem orderItem = OrderItem.builder()
                .order(this)
                .product(cartItem.getProduct())
                .build();

        orderItems.add(orderItem);
    }

    public long calcPayPrice() {
        return orderItems.stream()
                .mapToLong(OrderItem::getPayPrice)
                .sum();
    }

    public void setPaymentDone() {
        payDate = LocalDateTime.now();
    }

    public void setCancelDone() {
        cancelDate = LocalDateTime.now();
    }

    public void setRefundDone() {
        refundDate = LocalDateTime.now();
    }
}
