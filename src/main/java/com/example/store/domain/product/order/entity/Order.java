package com.example.store.domain.product.order.entity;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.cart.entity.CartItem;
import com.example.store.global.app.AppConfig;
import com.example.store.global.exceptions.GlobalException;
import com.example.store.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if (buyer.has(cartItem.getProduct()))
            throw new GlobalException("400-1", "이미 구매한 상품입니다.");

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

        orderItems.stream()
                .forEach(OrderItem::setPaymentDone);
    }

    public void setCancelDone() {
        cancelDate = LocalDateTime.now();

        orderItems.stream()
                .forEach(OrderItem::setCancelDone);
    }

    public void setRefundDone() {
        refundDate = LocalDateTime.now();

        orderItems.stream()
                .forEach(OrderItem::setRefundDone);
    }

    public String getName() {
        String name = orderItems.get(0).getProduct().getName();

        if (orderItems.size() > 1) {
            name += " 외 %d건".formatted(orderItems.size() - 1);
        }

        return name;
    }

    public String getCode() {
        // yyyy-MM-dd 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // LocalDateTime 객체를 문자열로 변환
        return getCreateDate().format(formatter) + (AppConfig.isNotProd() ? "-test-" + UUID.randomUUID().toString() : "") + "__" + getId();
    }

    public boolean isPayable() {
        if (payDate != null) return false;
        if (cancelDate != null) return false;

        return true;
    }
}
