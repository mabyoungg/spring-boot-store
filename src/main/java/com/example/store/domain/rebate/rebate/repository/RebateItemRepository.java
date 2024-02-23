package com.example.store.domain.rebate.rebate.repository;

import com.example.store.domain.rebate.rebate.entity.RebateItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RebateItemRepository extends JpaRepository<RebateItem, Long> {
    List<RebateItem> findByPayDateBetweenOrderByIdAsc(LocalDateTime fromDate, LocalDateTime toDate);
}
