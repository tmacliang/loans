package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hechao on 2017/10/18.
 */
public class PaymentOrderDTO implements PaymentOrderRequest, Serializable {
    private static final long serialVersionUID = 285935829537560108L;

    private Long accountId;

    private String paymentOrderNo;

    private String paymentStatusCode;

    private BigDecimal paymentAmount;

    private CurrencyCodeEnum currencyCode;

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public void setPaymentStatusCode(String paymentStatusCode) {
        this.paymentStatusCode = paymentStatusCode;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    @Override
    public String getPaymentStatusCode() {
        return paymentStatusCode;
    }

    @Override
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public CurrencyCodeEnum getCurrencyCode() {
        return currencyCode;
    }
}
