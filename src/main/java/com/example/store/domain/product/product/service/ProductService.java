package com.example.store.domain.product.product.service;

import com.example.store.domain.book.book.entity.Book;
import com.example.store.domain.product.product.entity.Product;
import com.example.store.domain.product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Book book) {
        if (book.getProduct() != null) return book.getProduct();

        Product product = Product.builder()
                .maker(book.getAuthor())
                .relTypeCode(book.getModelName())
                .relId(book.getId())
                .name(book.getTitle())
                .price(book.getPrice())
                .build();

        productRepository.save(product);

        book.setProduct(product);

        return product;
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }
}