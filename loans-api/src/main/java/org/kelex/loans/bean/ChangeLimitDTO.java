package org.kelex.loans.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by licl1 on 2017/11/1.
 */
public class ChangeLimitDTO implements ChangeLimitRequest, Serializable {
    private Long accountId;

    private Boolean permanent;

    private BigDecimal creditLimit;

    private LocalDate effectiveStartDate;

    private LocalDate effectiveEndDate;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setPermanent(Boolean permanent) {
        this.permanent = permanent;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setEffectiveStartDate(LocalDate effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public void setEffectiveEndDate(LocalDate effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    @Override
    public Long getAccountId() {
        return null;
    }

    @Override
    public Boolean getPermanent() {
        return null;
    }

    @Override
    public BigDecimal getCreditLimit() {
        return null;
    }

    @Override
    public LocalDate getEffectiveStartDate() {
        return null;
    }

    @Override
    public LocalDate getEffectiveEndDate() {
        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChangeLimitDTO{");
        sb.append("accountId=").append(accountId);
        sb.append(", permanent=").append(permanent);
        sb.append(", creditLimit=").append(creditLimit);
        sb.append(", effectiveStartDate=").append(effectiveStartDate);
        sb.append(", effectiveEndDate=").append(effectiveEndDate);
        sb.append('}');
        return sb.toString();
    }
}
