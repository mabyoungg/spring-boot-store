package com.example.store.domain.book.book.repository;

import com.example.store.domain.book.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
