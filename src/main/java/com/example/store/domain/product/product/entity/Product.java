package com.example.store.domain.product.product.entity;

import com.example.store.domain.book.book.entity.Book;
import com.example.store.domain.member.member.entity.Member;
import com.example.store.global.app.AppConfig;
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
public class Product extends BaseEntity {
    @ManyToOne
    private Member maker;
    private String relTypeCode;
    private long relId;
    private String name;
    private int price;

    public Book getBook() {
        return AppConfig.getEntityManager().getReference(Book.class, relId);
    }
}