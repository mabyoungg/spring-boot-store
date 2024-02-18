package com.example.store.domain.cash.withdraw.repository;

import com.example.store.domain.cash.withdraw.entity.WithdrawApply;
import com.example.store.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawApplyRepository extends JpaRepository<WithdrawApply, Long> {
    List<WithdrawApply> findByApplicantOrderByIdDesc(Member applicant);
}
