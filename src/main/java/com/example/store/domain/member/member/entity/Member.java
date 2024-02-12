package com.example.store.domain.member.member.entity;

import com.example.store.domain.book.book.entity.Book;
import com.example.store.domain.member.myBook.entity.MyBook;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

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
public class Member extends BaseEntity {
    private String username;
    private String password;
    private long restCash;

    @OneToMany(mappedBy = "owner", cascade = ALL, orphanRemoval = true)
    @Builder.Default
    private List<MyBook> myBooks = new ArrayList<>();

    public void addMyBook(Book book) {
        MyBook myBook = MyBook.builder()
                .owner(this)
                .book(book)
                .build();

        myBooks.add(myBook);
    }

    public void removeMyBook(Book book) {
        myBooks.removeIf(myBook -> myBook.getBook().equals(book));
    }

    public boolean hasBook(Book book) {
        return myBooks
                .stream()
                .anyMatch(myBook -> myBook.getBook().equals(book));
    }

    public boolean has(Product product) {
        return switch (product.getRelTypeCode()) {
            case "book" -> hasBook(product.getBook());
            default -> false;
        };
    }
}
