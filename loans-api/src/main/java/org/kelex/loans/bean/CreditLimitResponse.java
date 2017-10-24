package org.kelex.loans.bean;

import java.math.BigDecimal;

/**
 * Created by licl1 on 2017/9/18.
 */
public class CreditLimitResponse {

    private Long accountId;

    private BigDecimal creditLimit;

    private BigDecimal availableBalance;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
