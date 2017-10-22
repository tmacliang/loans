package org.kelex.loans.core.entity;

import org.kelex.loans.enumeration.CurrencyCode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hechao on 2017/9/7.
 */
@Entity
@Table(name = "act_process_ctrl")
public class ActProcessCtrlEntity extends DescriptionEntity implements Serializable{

    private static final long serialVersionUID = 7974710142401574068L;

    @Id
    @EmbeddedId
    private ProductId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false)
    private CurrencyCode currencyCode;

    @Column(name = "CURRENCY_UNIT", nullable = false)
    private String currencyUnit;

    @Column(name = "DEFAULT_TXN_FEE_RATE", nullable = false, precision = 5)
    private BigDecimal defaultTxnFeeRate;

    @Column(name = "DEFAULT_INTEREST_RATE", nullable = false, precision = 5)
    private BigDecimal defaultInterestRate;

    @Column(name = "DEFAULT_DLQ_FEE_RATE", nullable = false, precision = 5)
    private BigDecimal defaultDlqFeeRate;

    @Size(min = 1, max = 28)
    @Column(name = "DEFAULT_INTEREST_FREE_DAYS", nullable = false)
    private Integer defaultInterestFreeDays;

    @Column(name = "DEFAULT_CREDIT_LIMIT", nullable = false, precision = 2)
    private BigDecimal defaultCreditLimit;

    @Column(name = "DEFAULT_GRACE_DAYS", nullable = false)
    private Integer defaultGraceDays;

    public ProductId getId() {
        return id;
    }

    public void setId(ProductId id) {
        this.id = id;
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

    public BigDecimal getDefaultTxnFeeRate() {
        return defaultTxnFeeRate;
    }

    public void setDefaultTxnFeeRate(BigDecimal defaultTxnFeeRate) {
        this.defaultTxnFeeRate = defaultTxnFeeRate;
    }

    public BigDecimal getDefaultInterestRate() {
        return defaultInterestRate;
    }

    public void setDefaultInterestRate(BigDecimal defaultInterestRate) {
        this.defaultInterestRate = defaultInterestRate;
    }

    public BigDecimal getDefaultDlqFeeRate() {
        return defaultDlqFeeRate;
    }

    public void setDefaultDlqFeeRate(BigDecimal defaultDlqFeeRate) {
        this.defaultDlqFeeRate = defaultDlqFeeRate;
    }

    public Integer getDefaultInterestFreeDays() {
        return defaultInterestFreeDays;
    }

    public void setDefaultInterestFreeDays(Integer defaultInterestFreeDays) {
        this.defaultInterestFreeDays = defaultInterestFreeDays;
    }

    public BigDecimal getDefaultCreditLimit() {
        return defaultCreditLimit;
    }

    public void setDefaultCreditLimit(BigDecimal defaultCreditLimit) {
        this.defaultCreditLimit = defaultCreditLimit;
    }

    public Integer getDefaultGraceDays() {
        return defaultGraceDays;
    }

    public void setDefaultGraceDays(Integer defaultGraceDays) {
        this.defaultGraceDays = defaultGraceDays;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActProcessCtrlEntity{")
                .append("currencyCode = ").append(currencyCode)
                .append(",currencyUnit = '").append(currencyUnit).append('\'')
                .append(",defaultCreditLimit = ").append(defaultCreditLimit)
                .append(",defaultDlqFeeRate = ").append(defaultDlqFeeRate)
                .append(",defaultGraceDays = ").append(defaultGraceDays)
                .append(",defaultInterestFreeDays = ").append(defaultInterestFreeDays)
                .append(",defaultInterestRate = ").append(defaultInterestRate)
                .append(",defaultTxnFeeRate = ").append(defaultTxnFeeRate)
                .append(",id = ").append(id)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
