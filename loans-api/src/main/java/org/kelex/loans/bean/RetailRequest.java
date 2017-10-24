package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import java.math.BigDecimal;

/**
 * Created by hechao on 2017/9/15.
 */
public interface RetailRequest {
    /**
     * 交易者账号
     * @return
     */
    public Long getAccountId();

    /**
     * 交易订单号
     * @return
     */
    public String getRetailOrderNo();

    /**
     * 交易金额
     * @return
     */
    public BigDecimal getRetailAmount();

    /**
     * 分期数
     * @return
     */
    public Integer getTotalTerms();

    /**
     * 币种
     * @return
     */
    public CurrencyCodeEnum getCurrencyCode();

    /**
     * 商家名
     * @return
     */
    public String getMerchantName();

    /**
     * 商品编号
     * @return
     */
    public String getCommodityCode();

    /**
     * 交易描述
     * @return
     */
    public String getDescText();
}
