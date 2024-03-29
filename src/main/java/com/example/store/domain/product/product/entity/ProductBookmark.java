package com.example.store.domain.product.product.entity;

import com.example.store.domain.member.member.entity.Member;
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
public class ProductBookmark extends BaseEntity {
    @ManyToOne
    private Member member;
    @ManyToOne
    private Product product;
}
