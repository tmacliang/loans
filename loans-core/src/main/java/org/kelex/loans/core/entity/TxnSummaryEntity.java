package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;
import org.kelex.loans.core.enumeration.FlowType;
import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/9/27.
 */
@Entity
@Table(name = "txn_summary")
public class TxnSummaryEntity extends DescriptionEntity {

    private static final long serialVersionUID = -1101933759564010566L;

    @EmbeddedId
    private TxnSummaryId id;

    @Column(name = "TXN_ID", nullable = false, updatable = false)
    private Long txnId;

    @Column(name = "CUSTOMER_ID", nullable = false, updatable = false)
    private Long customerId;

    @Column(name = "IOU_ID", nullable = false, updatable = false)
    private Long iouId;

    @Column(name = "ORIGINAL_TXN_ID", nullable = false, updatable = false)
    private Long originalTxnId;

    @Column(name = "PRODUCT_ID", nullable = false, length = 8, updatable = false)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, updatable = false)
    private String actTypeId;

    @Column(name = "GEN_TXN_SUMMARY_NO", nullable = false)
    private Integer genTxnSummaryNo;

    @Column(name = "TERM_NO", nullable = false)
    private Integer termNo;

    @Column(name = "TXN_CODE", nullable = false, length = 5)
    private String txnCode;

    @Column(name = "TXN_TYPE", nullable = false, length = 5)
    private String txnType;

    @Column(name = "TXN_UUID", nullable = true, length = 64)
    private String txnUuid;

    @Column(name = "ORDER_NO", nullable = true, length = 64)
    private String orderNo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCodeEnum currencyCodeEnum;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "FLOW_TYPE", nullable = false, length = 1)
    private FlowType flowType;

    @Column(name = "TXN_AMT", nullable = false, precision = 2)
    private BigDecimal txnAmt;

    @Column(name = "POSTING_AMT", nullable = false, precision = 2)
    private BigDecimal postingAmt;

    @Column(name = "OUTSTANDING_DEDUCT_AMT", nullable = false, precision = 2)
    private BigDecimal outstandingDeductAmt;

    @Column(name = "GEN_FEE_AMT", nullable = false, precision = 2)
    private BigDecimal genFeeAmt;

    @Column(name = "REVERSAL_AMT", nullable = false, precision = 2)
    private BigDecimal reversalAmt;

    @Type(type = "yes_no")
    @Column(name = "CUSTOMER_GEN_FLAG", nullable = false, length = 1)
    private Boolean customerGenFlag;

    @Column(name = "TXN_DATE", nullable = false)
    private LocalDate txnDate;

    @Column(name = "TXN_TIME", nullable = false)
    private LocalTime txnTime;

    @Column(name = "MERCHANT_NAME", nullable = true, length = 64)
    private String merchantName;

    public TxnSummaryId getId() {
        return id;
    }

    public void setId(TxnSummaryId id) {
        this.id = id;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getIouId() {
        return iouId;
    }

    public void setIouId(Long iouId) {
        this.iouId = iouId;
    }

    public Long getOriginalTxnId() {
        return originalTxnId;
    }

    public void setOriginalTxnId(Long originalTxnId) {
        this.originalTxnId = originalTxnId;
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

    public Integer getGenTxnSummaryNo() {
        return genTxnSummaryNo;
    }

    public void setGenTxnSummaryNo(Integer genTxnSummaryNo) {
        this.genTxnSummaryNo = genTxnSummaryNo;
    }

    public Integer getTermNo() {
        return termNo;
    }

    public void setTermNo(Integer termNo) {
        this.termNo = termNo;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnUuid() {
        return txnUuid;
    }

    public void setTxnUuid(String txnUuid) {
        this.txnUuid = txnUuid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public CurrencyCodeEnum getCurrencyCodeEnum() {
        return currencyCodeEnum;
    }

    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCodeEnum) {
        this.currencyCodeEnum = currencyCodeEnum;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public BigDecimal getPostingAmt() {
        return postingAmt;
    }

    public void setPostingAmt(BigDecimal postingAmt) {
        this.postingAmt = postingAmt;
    }

    public BigDecimal getOutstandingDeductAmt() {
        return outstandingDeductAmt;
    }

    public void setOutstandingDeductAmt(BigDecimal outstandingDeductAmt) {
        this.outstandingDeductAmt = outstandingDeductAmt;
    }

    public BigDecimal getGenFeeAmt() {
        return genFeeAmt;
    }

    public void setGenFeeAmt(BigDecimal genFeeAmt) {
        this.genFeeAmt = genFeeAmt;
    }

    public BigDecimal getReversalAmt() {
        return reversalAmt;
    }

    public void setReversalAmt(BigDecimal reversalAmt) {
        this.reversalAmt = reversalAmt;
    }

    public Boolean getCustomerGenFlag() {
        return customerGenFlag;
    }

    public void setCustomerGenFlag(Boolean customerGenFlag) {
        this.customerGenFlag = customerGenFlag;
    }

    public LocalDate getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(LocalDate txnDate) {
        this.txnDate = txnDate;
    }

    public LocalTime getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(LocalTime txnTime) {
        this.txnTime = txnTime;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public Object primaryKey() {
        return txnId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TxnSummaryEntity{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",currencyCode = ").append(currencyCodeEnum)
                .append(",customerGenFlag = ").append(customerGenFlag)
                .append(",customerId = ").append(customerId)
                .append(",flowType = ").append(flowType)
                .append(",genFeeAmt = ").append(genFeeAmt)
                .append(",genTxnSummaryNo = ").append(genTxnSummaryNo)
                .append(",id = ").append(id)
                .append(",iouId = ").append(iouId)
                .append(",merchantName = '").append(merchantName).append('\'')
                .append(",orderNo = '").append(orderNo).append('\'')
                .append(",originalTxnId = ").append(originalTxnId)
                .append(",outstandingDeductAmt = ").append(outstandingDeductAmt)
                .append(",postingAmt = ").append(postingAmt)
                .append(",productId = '").append(productId).append('\'')
                .append(",reversalAmt = ").append(reversalAmt)
                .append(",termNo = ").append(termNo)
                .append(",txnAmt = ").append(txnAmt)
                .append(",txnCode = '").append(txnCode).append('\'')
                .append(",txnDate = ").append(txnDate)
                .append(",txnId = ").append(txnId)
                .append(",txnTime = ").append(txnTime)
                .append(",txnType = '").append(txnType).append('\'')
                .append(",txnUuid = '").append(txnUuid).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
