package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hechao on 2017/9/15.
 */
public class RetailDTO implements RetailRequest, Serializable{

    private static final long serialVersionUID = -3628986878481307280L;

    private Long accountId;

    private String retailOrderNo;

    private BigDecimal retailAmount;

    private Integer totalTerms;

    private CurrencyCode currencyCode;

    private String merchantName;

    private String commodityCode;

    private String descText;

    @Override
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String getRetailOrderNo() {
        return retailOrderNo;
    }

    public void setRetailOrderNo(String retailOrderNo) {
        this.retailOrderNo = retailOrderNo;
    }

    @Override
    public BigDecimal getRetailAmount() {
        return retailAmount;
    }

    public void setRetailAmount(BigDecimal retailAmount) {
        this.retailAmount = retailAmount;
    }

    @Override
    public Integer getTotalTerms() {
        return totalTerms;
    }

    public void setTotalTerms(Integer totalTerms) {
        this.totalTerms = totalTerms;
    }

    @Override
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    @Override
    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RetailDTO{");
        sb.append("accountId = ").append(accountId);
        sb.append(",commodityCode = '").append(commodityCode).append('\'');
        sb.append(",currencyCode = '").append(currencyCode).append('\'');
        sb.append(",descText = '").append(descText).append('\'');
        sb.append(",merchantName = '").append(merchantName).append('\'');
        sb.append(",retailAmount = ").append(retailAmount);
        sb.append(",retailOrderNo = '").append(retailOrderNo).append('\'');
        sb.append(",totalTerms = ").append(totalTerms);
        sb.append("}");
        return sb.toString();
    }
}
