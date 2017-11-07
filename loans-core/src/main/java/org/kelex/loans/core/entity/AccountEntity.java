package org.kelex.loans.core.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.kelex.loans.core.enumeration.FeeRateType;
import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by hechao on 2017/9/6.
 */
@Entity
@Table(name = "account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountEntity extends BaseEntity{
    private static final long serialVersionUID = 7398783906895247293L;
    @Id
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "LEVEL_NO", nullable = false)
    private Integer levelNo=1;

    @Column(name = "LEVEL_1_ACT_ID", nullable = false)
    private Long level1ActId;

    @Column(name = "LEVEL_2_ACT_ID")
    private Long level2ActId;

    @Column(name = "LEVEL_3_ACT_ID")
    private Long level3ActId;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "AUTO_STATUS_CODE", nullable = false, length = 4)
    private String autoStatusCode;

    @Column(name = "AUTO_STATUS_SET_DATE", nullable = false)
    private LocalDate autoStatusSetDate;

    @Column(name = "MANU_STATUS_CODE", nullable = false, length = 4)
    private String manuStatusCode;

    @Column(name = "MANU_STATUS_SET_DATE", nullable = false)
    private LocalDate manuStatusSetDate;

    @Column(name = "PRODUCT_ID", nullable = false, length = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, length = 8)
    private String actTypeId;

    @Column(name = "CURRENT_BALANCE", nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "CREDIT_LIMIT", nullable = false)
    private BigDecimal creditLimit;

    @Column(name = "CURRENT_CYCLE_NO", nullable = false)
    private Integer currentCycleNo;

    @Column(name = "CYCLE_TRIGGER_SEQ", nullable = false)
    private Long cycleTriggerSeq;

    @Column(name = "OUTSTANDING_TXN_AMT", nullable = false)
    private BigDecimal outstandingTxnAmt;

    @Column(name = "CURR_DUE_DATE")
    private LocalDate currDueDate;

    @Column(name = "PREFERRED_CYCLE_DAY", nullable = false)
    private Integer preferredCycleDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCodeEnum currencyCode;

    @Column(name = "CURR_CYCLE_DATE", nullable = false)
    private LocalDate currCycleDate;

    @Column(name = "PREV_CYCLE_DATE", nullable = false)
    private LocalDate prevCycleDate;

    @Type(type = "yes_no")
    @Column(name = "IN_DLQ_FLAG", nullable = false)
    private Boolean inDlqFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "TXN_FEE_RATE_TYPE", nullable = false)
    private FeeRateType txnFeeRateType;

    @Column(name = "TXN_FEE_RATE", nullable = false, precision = 5)
    private BigDecimal txnFeeRate;

    @Column(name = "INTEREST_RATE", nullable = false, precision = 5)
    private BigDecimal interestRate;

    @Column(name = "DLQ_FEE_RATE", nullable = false, precision = 5)
    private BigDecimal dlqFeeRate;

    @Column(name = "INTEREST_FREE_DAYS", nullable = false)
    private Integer interestFreeDays;

    @Type(type = "yes_no")
    @Column(name = "WAIVE_INTEREST_FLAG", nullable = false)
    private Boolean waiveInterestFlag;

    @Column(name = "WAIVE_INTEREST_START_DATE", nullable = false)
    private LocalDate waiveInterestStartDate;

    @Column(name = "WAIVE_INTEREST_END_DATE", nullable = false)
    private LocalDate waiveInterestEndDate;

    @Type(type = "yes_no")
    @Column(name = "WAIVE_TXN_FEE_FLAG", nullable = false)
    private Boolean waiveTxnFeeFlag;

    @Column(name = "WAIVE_TXN_FEE_START_DATE", nullable = false)
    private LocalDate waiveTxnFeeStartDate;

    @Column(name = "WAIVE_TXN_FEE_END_DATE", nullable = false)
    private LocalDate waiveTxnFeeEndDate;

    @Type(type = "yes_no")
    @Column(name = "WAIVE_OTHER_FEE_FLAG", nullable = false)
    private Boolean waiveOtherFeeFlag;

    @Column(name = "WAIVE_OTHER_FEE_START_DATE", nullable = false)
    private LocalDate waiveOtherFeeStartDate;

    @Column(name = "WAIVE_OTHER_FEE_END_DATE", nullable = false)
    private LocalDate waiveOtherFeeEndDate;

    @NotNull
    @Column(name = "total_interest_amt", precision = 17, scale = 2, nullable = false)
    private BigDecimal totalInterestAmt;

    @NotNull
    @Column(name = "total_txn_fee_amt", precision = 17, scale = 2, nullable = false)
    private BigDecimal totalTxnFeeAmt;

    @NotNull
    @Column(name = "total_service_fee_amt", precision = 17, scale = 2, nullable = false)
    private BigDecimal totalServiceFeeAmt;

    @NotNull
    @Column(name = "total_dlq_fee_amt", precision = 17, scale = 2, nullable = false)
    private BigDecimal totalDlqFeeAmt;

    @NotNull
    @Column(name = "total_other_fee_amt", precision = 17, scale = 2, nullable = false)
    private BigDecimal totalOtherFeeAmt;

    @Transient
    private CycleSummaryId cycleId;

    public boolean needAccrueInterest(){
        return  "DLQ-".equals(autoStatusCode) || inDlqFlag;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(Integer levelNo) {
        this.levelNo = levelNo;
    }

    public Long getLevel1ActId() {
        return level1ActId;
    }

    public void setLevel1ActId(Long level1ActId) {
        this.level1ActId = level1ActId;
    }

    public Long getLevel2ActId() {
        return level2ActId;
    }

    public void setLevel2ActId(Long level2ActId) {
        this.level2ActId = level2ActId;
    }

    public Long getLevel3ActId() {
        return level3ActId;
    }

    public void setLevel3ActId(Long level3ActId) {
        this.level3ActId = level3ActId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAutoStatusCode() {
        return autoStatusCode;
    }

    public void setAutoStatusCode(String autoStatusCode) {
        this.autoStatusCode = autoStatusCode;
    }

    public LocalDate getAutoStatusSetDate() {
        return autoStatusSetDate;
    }

    public void setAutoStatusSetDate(LocalDate autoStatusSetDate) {
        this.autoStatusSetDate = autoStatusSetDate;
    }

    public String getManuStatusCode() {
        return manuStatusCode;
    }

    public void setManuStatusCode(String manuStatusCode) {
        this.manuStatusCode = manuStatusCode;
    }

    public LocalDate getManuStatusSetDate() {
        return manuStatusSetDate;
    }

    public void setManuStatusSetDate(LocalDate manuStatusSetDate) {
        this.manuStatusSetDate = manuStatusSetDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getActTypeId() {
        return actTypeId;
    }

    public void setActTypeId(String actTypeId) {
        this.actTypeId = actTypeId;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Integer getCurrentCycleNo() {
        return currentCycleNo;
    }

    public void setCurrentCycleNo(Integer currentCycleNo) {
        this.currentCycleNo = currentCycleNo;
    }

    public Long getCycleTriggerSeq() {
        return cycleTriggerSeq;
    }

    public void setCycleTriggerSeq(Long cycleTriggerSeq) {
        this.cycleTriggerSeq = cycleTriggerSeq;
    }

    public BigDecimal getOutstandingTxnAmt() {
        return outstandingTxnAmt;
    }

    public void setOutstandingTxnAmt(BigDecimal outstandingTxnAmt) {
        this.outstandingTxnAmt = outstandingTxnAmt;
    }

    public LocalDate getCurrDueDate() {
        return currDueDate;
    }

    public void setCurrDueDate(LocalDate currDueDate) {
        this.currDueDate = currDueDate;
    }

    public Integer getPreferredCycleDay() {
        return preferredCycleDay;
    }

    public void setPreferredCycleDay(Integer preferredCycleDay) {
        this.preferredCycleDay = preferredCycleDay;
    }

    public CurrencyCodeEnum getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCodeEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    public LocalDate getCurrCycleDate() {
        return currCycleDate;
    }

    public void setCurrCycleDate(LocalDate currCycleDate) {
        this.currCycleDate = currCycleDate;
    }

    public LocalDate getPrevCycleDate() {
        return prevCycleDate;
    }

    public void setPrevCycleDate(LocalDate prevCycleDate) {
        this.prevCycleDate = prevCycleDate;
    }

    public Boolean getInDlqFlag() {
        return inDlqFlag;
    }

    public void setInDlqFlag(Boolean inDlqFlag) {
        this.inDlqFlag = inDlqFlag;
    }

    public FeeRateType getTxnFeeRateType() {
        return txnFeeRateType;
    }

    public void setTxnFeeRateType(FeeRateType txnFeeRateType) {
        this.txnFeeRateType = txnFeeRateType;
    }

    public BigDecimal getTxnFeeRate() {
        return txnFeeRate;
    }

    public void setTxnFeeRate(BigDecimal txnFeeRate) {
        this.txnFeeRate = txnFeeRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getDlqFeeRate() {
        return dlqFeeRate;
    }

    public void setDlqFeeRate(BigDecimal dlqFeeRate) {
        this.dlqFeeRate = dlqFeeRate;
    }

    public Integer getInterestFreeDays() {
        return interestFreeDays;
    }

    public void setInterestFreeDays(Integer interestFreeDays) {
        this.interestFreeDays = interestFreeDays;
    }

    public Boolean getWaiveInterestFlag() {
        return waiveInterestFlag;
    }

    public void setWaiveInterestFlag(Boolean waiveInterestFlag) {
        this.waiveInterestFlag = waiveInterestFlag;
    }

    public LocalDate getWaiveInterestStartDate() {
        return waiveInterestStartDate;
    }

    public void setWaiveInterestStartDate(LocalDate waiveInterestStartDate) {
        this.waiveInterestStartDate = waiveInterestStartDate;
    }

    public LocalDate getWaiveInterestEndDate() {
        return waiveInterestEndDate;
    }

    public void setWaiveInterestEndDate(LocalDate waiveInterestEndDate) {
        this.waiveInterestEndDate = waiveInterestEndDate;
    }

    public Boolean getWaiveTxnFeeFlag() {
        return waiveTxnFeeFlag;
    }

    public void setWaiveTxnFeeFlag(Boolean waiveTxnFeeFlag) {
        this.waiveTxnFeeFlag = waiveTxnFeeFlag;
    }

    public LocalDate getWaiveTxnFeeStartDate() {
        return waiveTxnFeeStartDate;
    }

    public void setWaiveTxnFeeStartDate(LocalDate waiveTxnFeeStartDate) {
        this.waiveTxnFeeStartDate = waiveTxnFeeStartDate;
    }

    public LocalDate getWaiveTxnFeeEndDate() {
        return waiveTxnFeeEndDate;
    }

    public void setWaiveTxnFeeEndDate(LocalDate waiveTxnFeeEndDate) {
        this.waiveTxnFeeEndDate = waiveTxnFeeEndDate;
    }

    public Boolean getWaiveOtherFeeFlag() {
        return waiveOtherFeeFlag;
    }

    public void setWaiveOtherFeeFlag(Boolean waiveOtherFeeFlag) {
        this.waiveOtherFeeFlag = waiveOtherFeeFlag;
    }

    public LocalDate getWaiveOtherFeeStartDate() {
        return waiveOtherFeeStartDate;
    }

    public void setWaiveOtherFeeStartDate(LocalDate waiveOtherFeeStartDate) {
        this.waiveOtherFeeStartDate = waiveOtherFeeStartDate;
    }

    public LocalDate getWaiveOtherFeeEndDate() {
        return waiveOtherFeeEndDate;
    }

    public void setWaiveOtherFeeEndDate(LocalDate waiveOtherFeeEndDate) {
        this.waiveOtherFeeEndDate = waiveOtherFeeEndDate;
    }

    public BigDecimal getTotalInterestAmt() {
        return totalInterestAmt;
    }

    public void setTotalInterestAmt(BigDecimal totalInterestAmt) {
        this.totalInterestAmt = totalInterestAmt;
    }

    public BigDecimal getTotalTxnFeeAmt() {
        return totalTxnFeeAmt;
    }

    public void setTotalTxnFeeAmt(BigDecimal totalTxnFeeAmt) {
        this.totalTxnFeeAmt = totalTxnFeeAmt;
    }

    public BigDecimal getTotalServiceFeeAmt() {
        return totalServiceFeeAmt;
    }

    public void setTotalServiceFeeAmt(BigDecimal totalServiceFeeAmt) {
        this.totalServiceFeeAmt = totalServiceFeeAmt;
    }

    public BigDecimal getTotalDlqFeeAmt() {
        return totalDlqFeeAmt;
    }

    public void setTotalDlqFeeAmt(BigDecimal totalDlqFeeAmt) {
        this.totalDlqFeeAmt = totalDlqFeeAmt;
    }

    public BigDecimal getTotalOtherFeeAmt() {
        return totalOtherFeeAmt;
    }

    public void setTotalOtherFeeAmt(BigDecimal totalOtherFeeAmt) {
        this.totalOtherFeeAmt = totalOtherFeeAmt;
    }

    public void setCycleId(CycleSummaryId cycleId) {
        this.cycleId = cycleId;
    }

    @Override
    public Object primaryKey() {
        return accountId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountEntity{");
        sb.append("accountId=").append(accountId);
        sb.append(", levelNo=").append(levelNo);
        sb.append(", level1ActId=").append(level1ActId);
        sb.append(", level2ActId=").append(level2ActId);
        sb.append(", level3ActId=").append(level3ActId);
        sb.append(", customerId=").append(customerId);
        sb.append(", autoStatusCode='").append(autoStatusCode).append('\'');
        sb.append(", autoStatusSetDate=").append(autoStatusSetDate);
        sb.append(", manuStatusCode='").append(manuStatusCode).append('\'');
        sb.append(", manuStatusSetDate=").append(manuStatusSetDate);
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", actTypeId='").append(actTypeId).append('\'');
        sb.append(", currentBalance=").append(currentBalance);
        sb.append(", creditLimit=").append(creditLimit);
        sb.append(", currentCycleNo=").append(currentCycleNo);
        sb.append(", cycleTriggerSeq=").append(cycleTriggerSeq);
        sb.append(", outstandingTxnAmt=").append(outstandingTxnAmt);
        sb.append(", currDueDate=").append(currDueDate);
        sb.append(", preferredCycleDay=").append(preferredCycleDay);
        sb.append(", currencyCode=").append(currencyCode);
        sb.append(", currCycleDate=").append(currCycleDate);
        sb.append(", prevCycleDate=").append(prevCycleDate);
        sb.append(", inDlqFlag=").append(inDlqFlag);
        sb.append(", txnFeeRateType=").append(txnFeeRateType);
        sb.append(", txnFeeRate=").append(txnFeeRate);
        sb.append(", interestRate=").append(interestRate);
        sb.append(", dlqFeeRate=").append(dlqFeeRate);
        sb.append(", interestFreeDays=").append(interestFreeDays);
        sb.append(", waiveInterestFlag=").append(waiveInterestFlag);
        sb.append(", waiveInterestStartDate=").append(waiveInterestStartDate);
        sb.append(", waiveInterestEndDate=").append(waiveInterestEndDate);
        sb.append(", waiveTxnFeeFlag=").append(waiveTxnFeeFlag);
        sb.append(", waiveTxnFeeStartDate=").append(waiveTxnFeeStartDate);
        sb.append(", waiveTxnFeeEndDate=").append(waiveTxnFeeEndDate);
        sb.append(", waiveOtherFeeFlag=").append(waiveOtherFeeFlag);
        sb.append(", waiveOtherFeeStartDate=").append(waiveOtherFeeStartDate);
        sb.append(", waiveOtherFeeEndDate=").append(waiveOtherFeeEndDate);
        sb.append(", totalInterestAmt=").append(totalInterestAmt);
        sb.append(", totalTxnFeeAmt=").append(totalTxnFeeAmt);
        sb.append(", totalServiceFeeAmt=").append(totalServiceFeeAmt);
        sb.append(", totalDlqFeeAmt=").append(totalDlqFeeAmt);
        sb.append(", totalOtherFeeAmt=").append(totalOtherFeeAmt);
        sb.append(", cycleId=").append(cycleId);
        sb.append(", imaryKey=").append(primaryKey());
        sb.append('}');
        return sb.toString();
    }

    /**
     * 得到账期ID
     * @return
     */
    public synchronized CycleSummaryId getCycleId(){
        if(cycleId==null){
            cycleId = new CycleSummaryId();
            cycleId.setAccountId(accountId);
            cycleId.setCycleNo(currentCycleNo);
        }
        return cycleId;
    }
}
