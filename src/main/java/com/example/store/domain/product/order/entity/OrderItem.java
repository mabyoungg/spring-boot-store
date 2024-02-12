package com.example.store.domain.product.order.entity;

import com.example.store.domain.product.product.entity.Product;
import com.example.store.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class OrderItem extends BaseEntity {
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;

    public long getPayPrice() {
        return product.getPrice();
    }

    public void setPaymentDone() {
        switch (product.getRelTypeCode()) {
            case "book" -> order.getBuyer().addMyBook(product.getBook());
        }
    }

    public void setCancelDone() {
    }

    public void setRefundDone() {
        switch (product.getRelTypeCode()) {
            case "book" -> order.getBuyer().removeMyBook(product.getBook());
        }
    }
}