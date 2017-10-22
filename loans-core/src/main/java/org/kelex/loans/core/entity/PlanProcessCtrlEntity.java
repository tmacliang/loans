package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;
import org.kelex.loans.core.enumeration.FeePayMethod;
import org.kelex.loans.core.enumeration.RemAdjustMethod;
import org.kelex.loans.enumeration.CurrencyCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by hechao on 2017/9/20.
 */
@Entity
@Table(name = "plan_process_ctrl")
public class PlanProcessCtrlEntity extends DescriptionEntity {

    private static final long serialVersionUID = -2408772265410037617L;

    @EmbeddedId
    private PlanProcessCtrlId id;

    @Type(type = "yes_no")
    @Column(name = "ACTIVE_FLAG", nullable = false, length = 1)
    private Boolean activeFlag;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCode currencyCode;

    @Column(name = "CURRENCY_UNIT", nullable = false, length = 5)
    private String currencyUnit;

    @Column(name = "FIRST_TXN_CODE", nullable = false, length = 4)
    private String firstTxnCode;

    @Column(name = "FIXED_TXN_CODE", nullable = false, length = 4)
    private String fixedTxnCode;

    @Column(name = "LAST_TXN_CODE", nullable = false, length = 4)
    private String lastTxnCode;

    @Column(name = "MIN_INSTALLMENT_AMT", nullable = false, scale = 2)
    private BigDecimal minInstallmentAmt;

    @Column(name = "SERVER_FEE_CODE", nullable = true, length = 4)
    private String serverFeeCode;

    @Column(name = "TXN_FEE_RATE", nullable = false, updatable = false)
    private BigDecimal txnFeeRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "REM_ADJUST_METHOD", nullable = false, length = 4)
    private RemAdjustMethod remAdjustMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "FEE_PAY_METHOD", nullable = false, length = 4)
    private FeePayMethod feePayMethod;

    public PlanProcessCtrlId getId() {
        return id;
    }

    public void setId(PlanProcessCtrlId id) {
        this.id = id;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    public String getFirstTxnCode() {
        return firstTxnCode;
    }

    public void setFirstTxnCode(String firstTxnCode) {
        this.firstTxnCode = firstTxnCode;
    }

    public String getFixedTxnCode() {
        return fixedTxnCode;
    }

    public void setFixedTxnCode(String fixedTxnCode) {
        this.fixedTxnCode = fixedTxnCode;
    }

    public String getLastTxnCode() {
        return lastTxnCode;
    }

    public void setLastTxnCode(String lastTxnCode) {
        this.lastTxnCode = lastTxnCode;
    }

    public String getServerFeeCode() {
        return serverFeeCode;
    }

    public void setServerFeeCode(String serverFeeCode) {
        this.serverFeeCode = serverFeeCode;
    }

    public BigDecimal getTxnFeeRate() {
        return txnFeeRate;
    }

    public void setTxnFeeRate(BigDecimal txnFeeRate) {
        this.txnFeeRate = txnFeeRate;
    }

    public BigDecimal getMinInstallmentAmt() {
        return minInstallmentAmt;
    }

    public void setMinInstallmentAmt(BigDecimal minInstallmentAmt) {
        this.minInstallmentAmt = minInstallmentAmt;
    }

    public RemAdjustMethod getRemAdjustMethod() {
        return remAdjustMethod;
    }

    public void setRemAdjustMethod(RemAdjustMethod remAdjustMethod) {
        this.remAdjustMethod = remAdjustMethod;
    }

    public FeePayMethod getFeePayMethod() {
        return feePayMethod;
    }

    public void setFeePayMethod(FeePayMethod feePayMethod) {
        this.feePayMethod = feePayMethod;
    }


    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlanProcessCtrlEntity{")
                .append("currencyUnit = '").append(currencyUnit).append('\'')
                .append(",activeFlag = ").append(activeFlag)
                .append(",currencyCode = ").append(currencyCode)
                .append(",feePayMethod = ").append(feePayMethod)
                .append(",firstTxnCode = '").append(firstTxnCode).append('\'')
                .append(",fixedTxnCode = '").append(fixedTxnCode).append('\'')
                .append(",id = ").append(id)
                .append(",lastTxnCode = '").append(lastTxnCode).append('\'')
                .append(",minInstallmentAmt = ").append(minInstallmentAmt)
                .append(",remAdjustMethod = ").append(remAdjustMethod)
                .append(",serverFeeCode = '").append(serverFeeCode).append('\'')
                .append(",txnFeeRate = ").append(txnFeeRate)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
