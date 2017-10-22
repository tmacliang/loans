package org.kelex.loans.core.util;

import java.math.BigDecimal;

/**
 * Created by hechao on 2017/10/12.
 */
public class InstalmentSample {
    private BigDecimal feeRate = BigDecimal.ZERO;

    private BigDecimal feeRateTimes = BigDecimal.ZERO;

    private BigDecimal firstTermAmt = BigDecimal.ZERO;

    private BigDecimal fixedTermAmt = BigDecimal.ZERO;

    private BigDecimal lastTermAmt = BigDecimal.ZERO;

    private BigDecimal firstTermFeeAmt = BigDecimal.ZERO;

    private BigDecimal fixedTermFeeAmt = BigDecimal.ZERO;

    private BigDecimal lastTermFeeAmt = BigDecimal.ZERO;

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getFeeRateTimes() {
        return feeRateTimes;
    }

    public void setFeeRateTimes(BigDecimal feeRateTimes) {
        this.feeRateTimes = feeRateTimes;
    }

    public BigDecimal getFirstTermAmt() {
        return firstTermAmt;
    }

    public void setFirstTermAmt(BigDecimal firstTermAmt) {
        this.firstTermAmt = firstTermAmt;
    }

    public BigDecimal getFixedTermAmt() {
        return fixedTermAmt;
    }

    public void setFixedTermAmt(BigDecimal fixedTermAmt) {
        this.fixedTermAmt = fixedTermAmt;
    }

    public BigDecimal getLastTermAmt() {
        return lastTermAmt;
    }

    public void setLastTermAmt(BigDecimal lastTermAmt) {
        this.lastTermAmt = lastTermAmt;
    }

    public BigDecimal getFirstTermFeeAmt() {
        return firstTermFeeAmt;
    }

    public void setFirstTermFeeAmt(BigDecimal firstTermFeeAmt) {
        this.firstTermFeeAmt = firstTermFeeAmt;
    }

    public BigDecimal getFixedTermFeeAmt() {
        return fixedTermFeeAmt;
    }

    public void setFixedTermFeeAmt(BigDecimal fixedTermFeeAmt) {
        this.fixedTermFeeAmt = fixedTermFeeAmt;
    }

    public BigDecimal getLastTermFeeAmt() {
        return lastTermFeeAmt;
    }

    public void setLastTermFeeAmt(BigDecimal lastTermFeeAmt) {
        this.lastTermFeeAmt = lastTermFeeAmt;
    }
}
