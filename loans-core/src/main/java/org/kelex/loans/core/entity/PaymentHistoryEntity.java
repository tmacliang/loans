package org.kelex.loans.core.entity;

import org.kelex.loans.enumeration.CurrencyCode;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/10/19.
 */
@Entity
@Table(name = "payment_history")
public class PaymentHistoryEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -5078113346222144809L;

    @Id
    @Column(name = "PAYMENT_NO", nullable = false)
    private long paymentNo;

    @Basic
    @Column(name = "PAYMENT_ORDER_NO", nullable = false, length = 64)
    private String paymentOrderNo;

    @Basic
    @Column(name = "STATUS_CODE", nullable = false, length = 4)
    private String statusCode;

    @Basic
    @Column(name = "CUSTOMER_ID", nullable = false)
    private long customerId;

    @Basic
    @Column(name = "ACCOUNT_ID", nullable = false)
    private long accountId;

    @Basic
    @Column(name = "IOU_ID", nullable = true)
    private Long iouId;

    @Basic
    @Column(name = "TXN_ID", nullable = true)
    private Long txnId;

    @Basic
    @Column(name = "PRODUCT_ID", nullable = false, length = 4)
    private String productId;

    @Basic
    @Column(name = "PAYMENT_AMT", nullable = false, precision = 2)
    private BigDecimal paymentAmt;

    @Basic
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCode currencyCode;

    @Basic
    @Column(name = "PAYMENT_METHOD", nullable = false, length = 5)
    private String paymentMethod;

    @Basic
    @Column(name = "REQUEST_DATE", nullable = false)
    private LocalDate requestDate;

    @Basic
    @Column(name = "REQUEST_TIME", nullable = false)
    private LocalTime requestTime;

    @Basic
    @Column(name = "RESPONSE_DATE", nullable = true)
    private LocalDate responseDate;

    @Basic
    @Column(name = "RESPONSE_TIME", nullable = true)
    private LocalTime responseTime;

    @Basic
    @Column(name = "COMPLETE_DATE", nullable = true)
    private LocalDate completeDate;

    public Long getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(long paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPaymentOrderNo() {
        return paymentOrderNo;
    }

    public void setPaymentOrderNo(String paymentOrderNo) {
        this.paymentOrderNo = paymentOrderNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Long getIouId() {
        return iouId;
    }

    public void setIouId(Long iouId) {
        this.iouId = iouId;
    }

    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalTime requestTime) {
        this.requestTime = requestTime;
    }

    public LocalDate getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(LocalDate responseDate) {
        this.responseDate = responseDate;
    }

    public LocalTime getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(LocalTime responseTime) {
        this.responseTime = responseTime;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    @Override
    public Object primaryKey() {
        return paymentOrderNo;
    }
}
