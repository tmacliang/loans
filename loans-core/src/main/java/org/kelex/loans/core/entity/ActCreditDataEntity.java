package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;
import org.kelex.loans.enumeration.CurrencyCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by hechao on 2017/9/13.
 */
@Entity
@Table(name = "act_credit_data")
public class ActCreditDataEntity extends BaseEntity{

    private static final long serialVersionUID = -9149009018496587843L;

    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCode currencyCode;

    @Column(name = "CREDIT_LIMIT", nullable = false, precision = 2)
    private BigDecimal creditLimit;

    @Type(type = "yes_no")
    @Column(name = "WHOLE_FLAG", nullable = false, length = 1)
    private Boolean wholeFlag;

    @Column(name = "TOTAL_POSTED_AMT", nullable = false, precision = 2)
    private BigDecimal totalPostedAmt;

    @Column(name = "OUTSTANDING_AUTH_AMT", nullable = false, precision = 2)
    private BigDecimal outstandingAuthAmt;

    @Column(name = "AVAILABLE_BALANCE", nullable = false, precision = 2)
    private BigDecimal availableBalance;

    @Column(name = "LAST_LIMIT_CHANGE_DATE", nullable = false)
    private LocalDate lastLimitChangeDate;

    @Column(name = "TEMP_CREDIT_LIMIT", nullable = false, precision = 2)
    private BigDecimal tempCreditLimit;

    @Column(name = "TEMP_CREDIT_START_DATE", nullable = false)
    private LocalDate tempCreditStartDate;

    @Column(name = "TEMP_CREDIT_END_DATE", nullable = false)
    private LocalDate tempCreditEndDate;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Boolean getWholeFlag() {
        return wholeFlag;
    }

    public void setWholeFlag(Boolean wholeFlag) {
        this.wholeFlag = wholeFlag;
    }

    public BigDecimal getTotalPostedAmt() {
        return totalPostedAmt;
    }

    public void setTotalPostedAmt(BigDecimal totalPostedAmt) {
        this.totalPostedAmt = totalPostedAmt;
    }

    public BigDecimal getOutstandingAuthAmt() {
        return outstandingAuthAmt;
    }

    public void setOutstandingAuthAmt(BigDecimal outstandingAuthAmt) {
        this.outstandingAuthAmt = outstandingAuthAmt;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public LocalDate getLastLimitChangeDate() {
        return lastLimitChangeDate;
    }

    public void setLastLimitChangeDate(LocalDate lastLimitChangeDate) {
        this.lastLimitChangeDate = lastLimitChangeDate;
    }

    public BigDecimal getTempCreditLimit() {
        return tempCreditLimit;
    }

    public void setTempCreditLimit(BigDecimal tempCreditLimit) {
        this.tempCreditLimit = tempCreditLimit;
    }

    public LocalDate getTempCreditStartDate() {
        return tempCreditStartDate;
    }

    public void setTempCreditStartDate(LocalDate tempCreditStartDate) {
        this.tempCreditStartDate = tempCreditStartDate;
    }

    public LocalDate getTempCreditEndDate() {
        return tempCreditEndDate;
    }

    public void setTempCreditEndDate(LocalDate tempCreditEndDate) {
        this.tempCreditEndDate = tempCreditEndDate;
    }

    @Override
    public Object primaryKey() {
        return accountId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActCreditDataEntity{")
                .append("accountId = ").append(accountId)
                .append(",availableBalance = ").append(availableBalance)
                .append(",creditLimit = ").append(creditLimit)
                .append(",currencyCode = ").append(currencyCode)
                .append(",lastLimitChangeDate = ").append(lastLimitChangeDate)
                .append(",outstandingAuthAmt = ").append(outstandingAuthAmt)
                .append(",tempCreditEndDate = ").append(tempCreditEndDate)
                .append(",tempCreditLimit = ").append(tempCreditLimit)
                .append(",tempCreditStartDate = ").append(tempCreditStartDate)
                .append(",totalPostedAmt = ").append(totalPostedAmt)
                .append(",wholeFlag = ").append(wholeFlag)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
