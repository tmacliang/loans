package org.kelex.loans.core.entity;

import org.kelex.loans.enumeration.CurrencyCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hechao on 2017/10/20.
 */
@Entity
@Table(name = "payment_process_ctrl")
public class PaymentProcessCtrlEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 8853788068081070597L;
    @EmbeddedId
    private PaymentProcessCtrlId id;

    @Basic
    @Column(name = "TXN_CODE", nullable = false, length = 4)
    private String txnCode;

    @Basic
    @Column(name = "INIT_STATUS_CODE", nullable = false, length = 4)
    private String initStatusCode;

    @Basic
    @Column(name = "STATUS_TYPE", nullable = false, length = 4)
    private String statusType;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false)
    private CurrencyCode currencyCode;

    @Basic
    @Column(name = "CURRENCY_UNIT", nullable = false)
    private String currencyUnit;

    public PaymentProcessCtrlId getId() {
        return id;
    }

    public void setId(PaymentProcessCtrlId id) {
        this.id = id;
    }

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getInitStatusCode() {
        return initStatusCode;
    }

    public void setInitStatusCode(String initStatusCode) {
        this.initStatusCode = initStatusCode;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
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

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PaymentProcessCtrlEntity{")
                .append("currencyCode = ").append(currencyCode)
                .append(",currencyUnit = '").append(currencyUnit).append('\'')
                .append(",id = ").append(id)
                .append(",initStatusCode = '").append(initStatusCode).append('\'')
                .append(",statusType = '").append(statusType).append('\'')
                .append(",txnCode = '").append(txnCode).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
