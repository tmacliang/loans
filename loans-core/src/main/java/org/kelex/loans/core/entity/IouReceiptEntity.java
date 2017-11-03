package org.kelex.loans.core.entity;

import org.kelex.loans.core.enumeration.FeeRateType;
import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/9/25.
 */
@Entity
@Table(name = "iou_receipt")
public class IouReceiptEntity extends DescriptionEntity implements Serializable{

    private static final long serialVersionUID = 4312422490551632310L;

    @Id
    @Column(name = "IOU_ID", nullable = false)
    private Long iouId;

    @Column(name = "PLAN_ID", nullable = false, length = 5)
    private String planId;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "CYCLE_NO", nullable = false)
    private Integer cycleNo;

    @Column(name = "COMMODITY_CODE", nullable = true, length = 64)
    private String commodityCode;

    @Column(name = "MERCHANT_NAME", nullable = true, length = 64)
    private String merchantName;

    @Column(name = "STATUS_CODE", nullable = false, length = 5)
    private String statusCode;

    @Column(name = "PVS_STATUS_CODE", nullable = false, length = 5)
    private String pvsStatusCode;

    @Column(name = "ORIGINAL_IOU_AMT", nullable = false, precision = 2)
    private BigDecimal originalIouAmt;

    @Column(name = "ORIGINAL_IOU_TERMS", nullable = false)
    private Integer originalIouTerms;

    @Column(name = "IOU_AMT", nullable = false, precision = 2)
    private BigDecimal iouAmt;

    @Column(name = "IOU_TERMS", nullable = false)
    private Integer iouTerms;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCodeEnum currencyCodeEnum;

    @Column(name = "TXN_UUID", nullable = false, length = 64)
    private String txnUuid;

    @Column(name = "ORDER_NO", nullable = false, length = 64)
    private String orderNo;

    @Column(name = "TXN_DATE", nullable = false)
    private LocalDate txnDate;

    @Column(name = "TXN_TIME", nullable = false)
    private LocalTime txnTime;

    @Column(name = "CURRENT_TERM_NO", nullable = false)
    private Integer currentTermNo;

    @Column(name = "POSTING_FEE_AMT", nullable = false)
    private BigDecimal postingFeeAmt;

    @Column(name = "CURRENT_BALANCE", nullable = false, precision = 2)
    private BigDecimal currentBalance;

    @Column(name = "OUTSTANDING_TXN_AMT", nullable = false, precision = 2)
    private BigDecimal outstandingTxnAmt;

    @Column(name = "OUTSTANDING_TERMS", nullable = false)
    private Integer outstandingTerms;

    @Column(name = "FIRST_TERM_AMT", nullable = false, precision = 2)
    private BigDecimal firstTermAmt;

    @Column(name = "FIXED_TERM_AMT", nullable = false, precision = 2)
    private BigDecimal fixedTermAmt;

    @Column(name = "LAST_TERM_AMT", nullable = false, precision = 2)
    private BigDecimal lastTermAmt;

    @Column(name = "TOTAL_PAYMENTS", nullable = false)
    private Integer totalPayments;

    @Column(name = "TOTAL_PAYMENT_AMT", nullable = false, precision = 2)
    private BigDecimal totalPaymentAmt;

    @Column(name = "LAST_PAYMENT_DATE", nullable = true)
    private LocalDate lastPaymentDate;

    @Column(name = "TOTAL_REVERSAL", nullable = false)
    private Integer totalReversal;

    @Column(name = "TOTAL_REVERSAL_AMT", nullable = false, precision = 0)
    private BigDecimal totalReversalAmt;

    @Column(name = "LAST_REVERSAL_DATE", nullable = true)
    private LocalDate lastReversalDate;

    @Column(name = "FEE_RATE", nullable = false, precision = 4)
    private BigDecimal feeRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "FEE_RATE_TYPE", nullable = false, length = 4)
    private FeeRateType feeRateType;

    @Column(name = "FIRST_TERM_FEE_AMT", nullable = false, scale = 2)
    private BigDecimal firstTermFeeAmt;

    @Column(name = "FIXED_TERM_FEE_AMT", nullable = false, scale = 2)
    private BigDecimal fixedTermFeeAmt;

    @Column(name = "LAST_TERM_FEE_AMT", nullable = false, scale = 2)
    private BigDecimal lastTermFeeAmt;

    @Column(name = "total_reversal_fee_amt", precision=17, scale=2, nullable = false)
    private BigDecimal totalReversalFeeAmt;

    @Column(name = "total_fee_amt", precision=17, scale=2, nullable = false)
    private BigDecimal totalFeeAmt;

    public BigDecimal getTotalReversalFeeAmt() {
        return totalReversalFeeAmt;
    }

    public void setTotalReversalFeeAmt(BigDecimal totalReversalFeeAmt) {
        this.totalReversalFeeAmt = totalReversalFeeAmt;
    }

    public BigDecimal getTotalFeeAmt() {
        return totalFeeAmt;
    }

    public void setTotalFeeAmt(BigDecimal totalFeeAmt) {
        this.totalFeeAmt = totalFeeAmt;
    }

    /**
     * 得到借据ID
     * @return
     */
    public Long getIouId() {
        return iouId;
    }

    /**
     * 设置借据ID
     * @param iouId
     */
    public void setIouId(Long iouId) {
        this.iouId = iouId;
    }

    /**
     * 得到计划ID
     * @return
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * 设置计划ID
     * @param planId
     */
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    /**
     * 得到账号ID
     * @return
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置账号ID
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 得到账期号
     * @return
     */
    public Integer getCycleNo() {
        return cycleNo;
    }

    /**
     * 设置账期号
     * @param cycleNo
     */
    public void setCycleNo(Integer cycleNo) {
        this.cycleNo = cycleNo;
    }

    /**
     * 得到商品编码
     * @return
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * 设置商品编码
     * @param commodityCode
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * 得到商家名称
     * @return
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * 设置商家名称
     * @param merchantName
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * 得到借据状态
     * @return
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * 设置借据状态
     * @param statusCode
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 得到前一次借据状态
     * @return
     */
    public String getPvsStatusCode() {
        return pvsStatusCode;
    }

    /**
     * 设置前一次借据状态
     * @param pvsStatusCode
     */
    public void setPvsStatusCode(String pvsStatusCode) {
        this.pvsStatusCode = pvsStatusCode;
    }

    /**
     * 得到原始借据金额
     * @return
     */
    public BigDecimal getOriginalIouAmt() {
        return originalIouAmt;
    }

    /**
     * 设置原始借据金额
     * @param originalIouAmt
     */
    public void setOriginalIouAmt(BigDecimal originalIouAmt) {
        this.originalIouAmt = originalIouAmt;
    }

    /**
     * 得到借据原始分期期数
     * @return
     */
    public Integer getOriginalIouTerms() {
        return originalIouTerms;
    }

    /**
     * 设置借据原始分期期数
     * @param originalIouTerms
     */
    public void setOriginalIouTerms(Integer originalIouTerms) {
        this.originalIouTerms = originalIouTerms;
    }

    /**
     * 得到借据本金
     * @return
     */
    public BigDecimal getIouAmt() {
        return iouAmt;
    }

    /**
     * 设置借据本金
     * @param iouAmt
     */
    public void setIouAmt(BigDecimal iouAmt) {
        this.iouAmt = iouAmt;
    }

    /**
     * 得到借据分期期数
     * @return
     */
    public Integer getIouTerms() {
        return iouTerms;
    }

    /**
     * 设置借据分期期数
     * @param iouTerms
     */
    public void setIouTerms(Integer iouTerms) {
        this.iouTerms = iouTerms;
    }

    /**
     * 得到币种
     * @return
     */
    public CurrencyCodeEnum getCurrencyCodeEnum() {
        return currencyCodeEnum;
    }

    /**
     * 设置币种
     * @param currencyCodeEnum
     */
    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCodeEnum) {
        this.currencyCodeEnum = currencyCodeEnum;
    }

    /**
     * 得到交易唯一ID
     * @return
     */
    public String getTxnUuid() {
        return txnUuid;
    }

    /**
     * 设置交易唯一ID
     * @param txnUuid
     */
    public void setTxnUuid(String txnUuid) {
        this.txnUuid = txnUuid;
    }

    /**
     * 得到单号
     * @return
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * 设置单号
     * @param orderNo
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 得到交易时间
     * @return
     */
    public LocalDate getTxnDate() {
        return txnDate;
    }

    /**
     * 设置交易日期
     * @param txnDate
     */
    public void setTxnDate(LocalDate txnDate) {
        this.txnDate = txnDate;
    }

    /**
     * 得到交易时间
     * @return
     */
    public LocalTime getTxnTime() {
        return txnTime;
    }

    /**
     * 设置交易时间
     * @param txnTime
     */
    public void setTxnTime(LocalTime txnTime) {
        this.txnTime = txnTime;
    }

    /**
     * 得到当前分期号
     * @return
     */
    public Integer getCurrentTermNo() {
        return currentTermNo;
    }

    /**
     * 设置当前分期号
     * @param currentTermNo
     */
    public void setCurrentTermNo(Integer currentTermNo) {
        this.currentTermNo = currentTermNo;
    }

    /**
     * 得到当前余额
     * @return
     */
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    /**
     * 设置当前余额
     * @param currentBalance
     */
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     * 得到实际入账的手续费金额
     * @return
     */
    public BigDecimal getPostingFeeAmt() {
        return postingFeeAmt;
    }

    /**
     * 设置实际入账的手续费金额
     * @return
     */
    public void setPostingFeeAmt(BigDecimal postingFeeAmt) {
        this.postingFeeAmt = postingFeeAmt;
    }

    /**
     * 得到未入账本金
     * @return
     */
    public BigDecimal getOutstandingTxnAmt() {
        return outstandingTxnAmt;
    }

    /**
     * 设置未入账本金
     * @param outstandingTxnAmt
     */
    public void setOutstandingTxnAmt(BigDecimal outstandingTxnAmt) {
        this.outstandingTxnAmt = outstandingTxnAmt;
    }

    /**
     * 得到未入账分期期数
     * @return
     */
    public Integer getOutstandingTerms() {
        return outstandingTerms;
    }

    /**
     * 设置未入账分期期数
     * @param outstandingTerms
     */
    public void setOutstandingTerms(Integer outstandingTerms) {
        this.outstandingTerms = outstandingTerms;
    }

    /**
     * 得到首期还款本金
     * @return
     */
    public BigDecimal getFirstTermAmt() {
        return firstTermAmt;
    }

    /**
     * 设置首期还款本金
     * @param firstTermAmt
     */
    public void setFirstTermAmt(BigDecimal firstTermAmt) {
        this.firstTermAmt = firstTermAmt;
    }

    /**
     * 得到除首末期外分期还款本金
     * @return
     */
    public BigDecimal getFixedTermAmt() {
        return fixedTermAmt;
    }

    /**
     * 设置除首末期外分期还款本金
     * @param fixedTermAmt
     */
    public void setFixedTermAmt(BigDecimal fixedTermAmt) {
        this.fixedTermAmt = fixedTermAmt;
    }

    /**
     * 得到末期还款金额
     * @return
     */
    public BigDecimal getLastTermAmt() {
        return lastTermAmt;
    }

    /**
     * 设置末期还款金额
     * @param lastTermAmt
     */
    public void setLastTermAmt(BigDecimal lastTermAmt) {
        this.lastTermAmt = lastTermAmt;
    }

    /**
     * 得到已还款次数
     * @return
     */
    public Integer getTotalPayments() {
        return totalPayments;
    }

    /**
     * 设置已还款次数
     * @param totalPayments
     */
    public void setTotalPayments(Integer totalPayments) {
        this.totalPayments = totalPayments;
    }

    /**
     * 得到总还款金额
     * @return
     */
    public BigDecimal getTotalPaymentAmt() {
        return totalPaymentAmt;
    }

    /**
     * 设置总还款金额
     * @param totalPaymentAmt
     */
    public void setTotalPaymentAmt(BigDecimal totalPaymentAmt) {
        this.totalPaymentAmt = totalPaymentAmt;
    }

    /**
     * 得到最后一次还款的日期
     * @return
     */
    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    /**
     * 设置最后一次还款的日期
     * @param lastPaymentDate
     */
    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    /**
     * 得到总退款次数
     * @return
     */
    public Integer getTotalReversal() {
        return totalReversal;
    }

    /**
     * 设置总退款次数
     * @param totalReversal
     */
    public void setTotalReversal(Integer totalReversal) {
        this.totalReversal = totalReversal;
    }

    /**
     * 得到总退款金额
     * @return
     */
    public BigDecimal getTotalReversalAmt() {
        return totalReversalAmt;
    }

    /**
     * 设置总退款金额
     * @param totalReversalAmt
     */
    public void setTotalReversalAmt(BigDecimal totalReversalAmt) {
        this.totalReversalAmt = totalReversalAmt;
    }

    /**
     * 得到最后一次退款的日期
     * @return
     */
    public LocalDate getLastReversalDate() {
        return lastReversalDate;
    }

    /**
     * 设置最后一次退款的日期
     * @param lastReversalDate
     */
    public void setLastReversalDate(LocalDate lastReversalDate) {
        this.lastReversalDate = lastReversalDate;
    }

    /**
     * 得到手续费率
     * @return
     */
    public BigDecimal getFeeRate() {
        return feeRate;
    }

    /**
     * 设置手续费率
     * @param feeRate
     */
    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    /**
     * 得到手偶续费率类型
     * @return
     */
    public FeeRateType getFeeRateType() {
        return feeRateType;
    }

    /**
     * 得到首期分期手续费率
     * @return
     */
    public BigDecimal getFirstTermFeeAmt() {
        return firstTermFeeAmt;
    }

    /**
     * 设置首期分期手续费率
     * @param firstTermFeeAmt
     */
    public void setFirstTermFeeAmt(BigDecimal firstTermFeeAmt) {
        this.firstTermFeeAmt = firstTermFeeAmt;
    }

    /**
     * 得到固定器分期手续费率
     * @return
     */
    public BigDecimal getFixedTermFeeAmt() {
        return fixedTermFeeAmt;
    }

    /**
     * 设置固定器分期手续费率
     * @param fixedTermFeeAmt
     */
    public void setFixedTermFeeAmt(BigDecimal fixedTermFeeAmt) {
        this.fixedTermFeeAmt = fixedTermFeeAmt;
    }

    /**
     * 得到末期分期手续费率
     * @return
     */
    public BigDecimal getLastTermFeeAmt() {
        return lastTermFeeAmt;
    }

    /**
     * 设置末期分期手续费率
     * @param lastTermFeeAmt
     */
    public void setLastTermFeeAmt(BigDecimal lastTermFeeAmt) {
        this.lastTermFeeAmt = lastTermFeeAmt;
    }

    /**
     * 设置手续费率类型
     * @param feeRateType
     */
    public void setFeeRateType(FeeRateType feeRateType) {
        this.feeRateType = feeRateType;
    }

    @Override
    public Object primaryKey() {
        return accountId;
    }


    /**
     * 当前是否处于首期
     * @return
     */
    public boolean isFirstTerm(){
        return currentTermNo.compareTo(1)==0;
    }

    /**
     * 当前是否处于末期
     * @return
     */
    public boolean isLastTerm(){
        return currentTermNo.compareTo(iouTerms)==0;
    }

    /**
     * 当前是否不处于首期或末期
     * @return
     */
    public boolean isFixedTerm(){
        return !(isFirstTerm() || isLastTerm());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IouReceiptEntity{");
        sb.append("iouId=").append(iouId);
        sb.append(", planId='").append(planId).append('\'');
        sb.append(", accountId=").append(accountId);
        sb.append(", cycleNo=").append(cycleNo);
        sb.append(", commodityCode='").append(commodityCode).append('\'');
        sb.append(", merchantName='").append(merchantName).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", pvsStatusCode='").append(pvsStatusCode).append('\'');
        sb.append(", originalIouAmt=").append(originalIouAmt);
        sb.append(", originalIouTerms=").append(originalIouTerms);
        sb.append(", iouAmt=").append(iouAmt);
        sb.append(", iouTerms=").append(iouTerms);
        sb.append(", currencyCodeEnum=").append(currencyCodeEnum);
        sb.append(", txnUuid='").append(txnUuid).append('\'');
        sb.append(", orderNo='").append(orderNo).append('\'');
        sb.append(", txnDate=").append(txnDate);
        sb.append(", txnTime=").append(txnTime);
        sb.append(", currentTermNo=").append(currentTermNo);
        sb.append(", postingFeeAmt=").append(postingFeeAmt);
        sb.append(", currentBalance=").append(currentBalance);
        sb.append(", outstandingTxnAmt=").append(outstandingTxnAmt);
        sb.append(", outstandingTerms=").append(outstandingTerms);
        sb.append(", firstTermAmt=").append(firstTermAmt);
        sb.append(", fixedTermAmt=").append(fixedTermAmt);
        sb.append(", lastTermAmt=").append(lastTermAmt);
        sb.append(", totalPayments=").append(totalPayments);
        sb.append(", totalPaymentAmt=").append(totalPaymentAmt);
        sb.append(", lastPaymentDate=").append(lastPaymentDate);
        sb.append(", totalReversal=").append(totalReversal);
        sb.append(", totalReversalAmt=").append(totalReversalAmt);
        sb.append(", lastReversalDate=").append(lastReversalDate);
        sb.append(", feeRate=").append(feeRate);
        sb.append(", feeRateType=").append(feeRateType);
        sb.append(", firstTermFeeAmt=").append(firstTermFeeAmt);
        sb.append(", fixedTermFeeAmt=").append(fixedTermFeeAmt);
        sb.append(", lastTermFeeAmt=").append(lastTermFeeAmt);
        sb.append(", totalReversalFeeAmt=").append(totalReversalFeeAmt);
        sb.append(", totalFeeAmt=").append(totalFeeAmt);
        sb.append(", imaryKey=").append(primaryKey());
        sb.append(", firstTerm=").append(isFirstTerm());
        sb.append(", lastTerm=").append(isLastTerm());
        sb.append(", fixedTerm=").append(isFixedTerm());
        sb.append('}');
        return sb.toString();
    }
}
