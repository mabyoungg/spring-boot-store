package com.example.store.domain.product.product.controller;

import com.example.store.domain.product.product.entity.Product;
import com.example.store.domain.product.product.entity.ProductBookmark;
import com.example.store.domain.product.product.service.ProductBookmarkService;
import com.example.store.domain.product.product.service.ProductService;
import com.example.store.global.exceptions.GlobalException;
import com.example.store.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final Rq rq;
    private final ProductService productService;
    private final ProductBookmarkService productBookmarkService;

    @GetMapping("/bookmarkList")
    public String showBookmarkList() {
        List<ProductBookmark> productBookmarks = productBookmarkService.findByMember(rq.getMember());

        rq.attr("productBookmarks", productBookmarks);

        return "domain/product/product/bookmarkList";
    }

    @GetMapping("/list")
    public String showList(
            @RequestParam(value = "kwType", defaultValue = "name") List<String> kwTypes,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Product> itemsPage = productService.search(null, true, kwTypes, kw, pageable);
        model.addAttribute("itemPage", itemsPage);
        model.addAttribute("kwTypesMap", kwTypesMap);
        model.addAttribute("page", page);

        return "domain/product/product/list";
    }

    @GetMapping("/myList")
    public String showMyList(
            @RequestParam(value = "kwType", defaultValue = "name") List<String> kwTypes,
            @RequestParam(defaultValue = "") String kw,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Product> itemsPage = productService.search(rq.getMember(), null, kwTypes, kw, pageable);
        model.addAttribute("itemPage", itemsPage);
        model.addAttribute("kwTypesMap", kwTypesMap);
        model.addAttribute("page", page);

        return "domain/product/product/myList";
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@PathVariable long id) {
        return null;
    }

    @PostMapping("/{id}/bookmark")
    @PreAuthorize("isAuthenticated()")
    public String bookmark(
            @PathVariable long id,
            @RequestParam(defaultValue = "/") String redirectUrl
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new GlobalException("400", "존재하지 않는 상품입니다."));
        productService.bookmark(rq.getMember(), product);

        return rq.redirect(redirectUrl, null);
    }

    @DeleteMapping("/{id}/cancelBookmark")
    @PreAuthorize("isAuthenticated()")
    public String cancelBookmark(
            @PathVariable long id,
            @RequestParam(defaultValue = "/") String redirectUrl
    ) {
        Product product = productService.findById(id).orElseThrow(() -> new GlobalException("400", "존재하지 않는 상품입니다."));
        productService.cancelBookmark(rq.getMember(), product);

        return rq.redirect(redirectUrl, null);
    }
}
