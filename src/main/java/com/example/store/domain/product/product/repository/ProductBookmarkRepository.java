package com.example.store.domain.product.product.repository;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.domain.product.product.entity.ProductBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBookmarkRepository extends JpaRepository<ProductBookmark, Long> {
    boolean existsByMemberAndProduct(Member actor, Product product);

    void deleteByMemberAndProduct(Member member, Product product);
}
