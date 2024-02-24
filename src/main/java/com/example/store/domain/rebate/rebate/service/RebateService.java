package com.example.store.domain.rebate.rebate.service;

import com.example.store.domain.cash.cash.entity.CashLog;
import com.example.store.domain.member.member.entity.Member;
import com.example.store.domain.member.member.service.MemberService;
import com.example.store.domain.product.order.entity.OrderItem;
import com.example.store.domain.product.order.service.OrderService;
import com.example.store.domain.rebate.rebate.entity.RebateItem;
import com.example.store.domain.rebate.rebate.repository.RebateItemRepository;
import com.example.store.global.exceptions.GlobalException;
import com.example.store.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RebateService {
    private final RebateItemRepository rebateItemRepository;
    private final OrderService orderService;
    private final MemberService memberService;

    @Transactional
    public void make(String yearMonth) {

        String[] yearMonthBits = yearMonth.split("-", 2);

        int year = Integer.parseInt(yearMonthBits[0]);
        int month = Integer.parseInt(yearMonthBits[1]);

        YearMonth yearMonth_ = YearMonth.of(year, month); // 연월 지정
        LocalDateTime startDate = yearMonth_.atDay(1).atStartOfDay(); // 해당 월의 첫 날 00:00
        LocalDateTime endDate = yearMonth_.atEndOfMonth().atTime(23, 59, 59, 999999999); // 해당 월의 마지막 날 23:59:59.999999999

        List<OrderItem> orderItems = orderService.findNotRebatedAndNotRefundedByPayDateBetween(startDate, endDate);

        orderItems
                .stream()
                .forEach(orderItem -> {
                    RebateItem rebateItem = RebateItem.builder()
                            .payDate(orderItem.getOrder().getPayDate())
                            .eventDate(orderItem.getOrder().getPayDate())
                            .rebateRate(orderItem.getRebateRate())
                            .payPrice(orderItem.getPayPrice())
                            .rebatePrice((long) Math.ceil(orderItem.getPayPrice() * orderItem.getRebateRate()))
                            .orderItem(orderItem)
                            .buyer(orderItem.getOrder().getBuyer())
                            .seller(orderItem.getProduct().getMaker())
                            .product(orderItem.getProduct())
                            .build();

                    rebateItemRepository.save(rebateItem);

                    orderItem.setRebateItem(rebateItem);
                });
    }

    public List<RebateItem> findByPayDateIn(String yearMonth) {
        int monthEndDay = Ut.date.getEndDayOf(yearMonth);

        String fromDateStr = yearMonth + "-01 00:00:00.000000";
        String toDateStr = yearMonth + "-%02d 23:59:59.999999".formatted(monthEndDay);
        LocalDateTime fromDate = Ut.date.parse(fromDateStr);
        LocalDateTime toDate = Ut.date.parse(toDateStr);

        return rebateItemRepository.findByPayDateBetweenOrderByIdAsc(fromDate, toDate);
    }

    public Optional<RebateItem> findById(long id) {
        return rebateItemRepository.findById(id);
    }

    @Transactional
    public void rebate(RebateItem rebateItem) {
        if (!rebateItem.isRebateAvailable()) {
            throw new GlobalException("400", "정산을 할 수 없는 상태입니다.");
        }

        long rebatePrice = rebateItem.getRebatePrice();

        memberService.addCash(
                rebateItem.getSeller(),
                rebatePrice,
                CashLog.EvenType.작가정산__예치금,
                rebateItem
        );

        rebateItem.setRebateDone();
    }

    public boolean canRebate(Member actor, RebateItem rebateItem) {
        return actor.isAdmin() && rebateItem.isRebateAvailable();
    }
}
