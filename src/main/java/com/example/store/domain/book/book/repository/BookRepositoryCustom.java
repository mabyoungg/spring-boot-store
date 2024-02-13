package com.example.store.domain.book.book.repository;

import com.example.store.domain.book.book.entity.Book;
import com.example.store.domain.member.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepositoryCustom {
    Page<Book> search(Member author, Boolean published, List<String> kwTypes, String kw, Pageable pageable);
}
