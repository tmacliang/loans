package org.kelex.loans.bean;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import java.math.BigDecimal;

/**
 * Created by hechao on 2017/10/18.
 */
public interface PrePaymentRequest {
    Long getAccountId();

    String getPaymentOrderNo();

    String getPaymentType();

    BigDecimal getPaymentAmount();

    CurrencyCodeEnum getCurrencyCode();
}
