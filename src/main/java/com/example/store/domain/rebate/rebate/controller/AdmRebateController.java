package com.example.store.domain.rebate.rebate.controller;

import com.example.store.domain.product.order.entity.OrderItem;
import com.example.store.domain.product.order.service.OrderService;
import com.example.store.domain.rebate.rebate.service.RebateService;
import com.example.store.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm/rebate")
@RequiredArgsConstructor
public class AdmRebateController {
    private final OrderService orderService;
    private final RebateService rebateService;
    private final Rq rq;

    @GetMapping("/make")
    public String showMake() {
        return "domain/rebate/rebate/adm/make";
    }

    @PostMapping("/make")
    public String make(
            String yearMonth
    ) {
        rebateService.make(yearMonth);

        return rq.redirect("/adm/rebate/make", "정산데이터를 생성했습니다.");
    }
}
