package com.example.store.domain.cash.cash.service;

import com.example.store.domain.cash.cash.entity.CashLog;
import com.example.store.domain.cash.cash.repository.CashLogRepository;
import com.example.store.domain.member.member.entity.Member;
import com.example.store.global.jpa.BaseTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CashService {
    private final CashLogRepository cashLogRepository;

    @Transactional
    public CashLog addCash(Member member, long price, CashLog.EvenType eventType, BaseTime relEntity) {
        CashLog cashLog = CashLog.builder()
                .member(member)
                .price(price)
                .relTypeCode(relEntity.getModelName())
                .relId(relEntity.getId())
                .eventType(eventType)
                .build();

        cashLogRepository.save(cashLog);

        return cashLog;
    }
}
