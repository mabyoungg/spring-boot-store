package com.example.store.domain.cash.withdraw.entity;

import com.example.store.domain.member.member.entity.Member;
import com.example.store.global.jpa.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class WithdrawApply extends BaseTime {
    private LocalDateTime withdrawDate;
    private LocalDateTime cancelDate;
    @ManyToOne
    private Member applicant;
    private String bankName;
    private String bankAccountNo;
    private long cash;

    public boolean isWithdrawDone() {
        return withdrawDate != null;
    }

    public void setWithdrawDone() {
        withdrawDate = LocalDateTime.now();
    }

    public String getForPrintWithdrawStatus() {
        if (withdrawDate != null)
            return "처리완료(" + withdrawDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")";

        if (withdrawDate == null) return "-";

        return "처리가능";
    }
}
