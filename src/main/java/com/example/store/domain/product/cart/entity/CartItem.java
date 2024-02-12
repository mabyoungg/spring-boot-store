package com.example.store.domain.product.cart.entity;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class CartItem extends BaseEntity {
    @ManyToOne
    private Member buyer;
    @ManyToOne
    private Product product;
}
