package com.example.store.domain.rebate.rebate.repository;

import com.example.store.domain.rebate.rebate.entity.RebateItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebateItemRepository extends JpaRepository<RebateItem, Long> {
}
