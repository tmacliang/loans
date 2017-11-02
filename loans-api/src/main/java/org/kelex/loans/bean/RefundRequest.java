package org.kelex.loans.bean;

import java.math.BigDecimal;

/**
 * Created by licl1 on 2017/11/2.
 */
public interface RefundRequest {

    Long getIouId();

    String getRefundOrderNo();

    BigDecimal getRefundAmount();

    String getCurrencyCode();
    
}
