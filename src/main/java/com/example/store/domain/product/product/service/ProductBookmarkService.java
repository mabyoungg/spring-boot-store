package com.example.store.domain.product.product.service;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.domain.product.product.entity.ProductBookmark;
import com.example.store.domain.product.product.repository.ProductBookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductBookmarkService {
    private final ProductBookmarkRepository productBookmarkRepository;

    @Transactional
    public void bookmark(Member member, Product product) {
        ProductBookmark productBookmark = ProductBookmark.builder()
                .member(member)
                .product(product)
                .build();

        productBookmarkRepository.save(productBookmark);
    }

    @Transactional
    public void cancelBookmark(Member member, Product product) {
        productBookmarkRepository.deleteByMemberAndProduct(member, product);
    }

    public boolean canBookmark(Member actor, Product product) {
        if (actor == null) return false;

        return !productBookmarkRepository.existsByMemberAndProduct(actor, product);
    }

    public boolean canCancelBookmark(Member actor, Product product) {
        if (actor == null) return false;

        return productBookmarkRepository.existsByMemberAndProduct(actor, product);
    }
}
