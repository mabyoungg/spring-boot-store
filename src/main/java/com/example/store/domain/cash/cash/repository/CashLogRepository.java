package com.example.store.domain.cash.cash.repository;

import com.example.store.domain.cash.cash.entity.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashLogRepository extends JpaRepository<CashLog, Long> {
}
