package com.example.store.domain.rebate.rebate.controller;

import com.example.store.domain.product.order.service.OrderService;
import com.example.store.domain.rebate.rebate.entity.RebateItem;
import com.example.store.domain.rebate.rebate.service.RebateService;
import com.example.store.global.exceptions.GlobalException;
import com.example.store.global.rq.Rq;
import com.example.store.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/list")
    public String showList(String yearMonth, Model model) {
        if (Ut.str.isBlank(yearMonth)) {
            yearMonth = Ut.date.getCurrentYearMonth();
        }

        List<RebateItem> items = rebateService.findByPayDateIn(yearMonth);

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("items", items);

        return "domain/rebate/rebate/adm/list";
    }

    @PostMapping("/{id}/rebate")
    public String rebate(
            @PathVariable long id,
            String redirectUrl
    ) {
        RebateItem rebateItem = rebateService.findById(id).orElseThrow(() -> new GlobalException("400", "정산데이터가 존재하지 않습니다."));

        rebateService.rebate(rebateItem);

        return rq.redirect(redirectUrl, "%d번 정산데이터를 처리하였습니다.".formatted(rebateItem.getId()));
    }
}
