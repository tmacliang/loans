package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by licl1 on 2017/11/2.
 */
public class RefundDTO implements RefundRequest, Serializable {

    private static final long serialVersionUID = -1096756705101668402L;
    private Long iouId;

    private String refundOrderNo;

    private BigDecimal refundAmount;

    private CurrencyCodeEnum currencyCode;

    public void setIouId(Long iouId) {
        this.iouId = iouId;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public void setCurrencyCode(CurrencyCodeEnum currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public Long getIouId() {
        return this.iouId;
    }

    @Override
    public String getRefundOrderNo() {
        return this.refundOrderNo;
    }

    @Override
    public BigDecimal getRefundAmount() {
        return this.refundAmount;
    }

    @Override
    public CurrencyCodeEnum getCurrencyCode() {
        return this.currencyCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RefundDTO{");
        sb.append("iouId=").append(iouId);
        sb.append(", refundOrderNo='").append(refundOrderNo).append('\'');
        sb.append(", refundAmount=").append(refundAmount);
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
