package com.example.store.domain.book.book.entity;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.global.jpa.BaseTime;
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
public class Book extends BaseTime {
    @ManyToOne
    private Member author;
    @OneToOne
    private Product product;
    private String title;
    private String body;
    private int price;
    private boolean published;
}