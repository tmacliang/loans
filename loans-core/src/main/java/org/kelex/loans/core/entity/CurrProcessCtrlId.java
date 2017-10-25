package org.kelex.loans.core.entity;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/21.
 */
@Embeddable
public class CurrProcessCtrlId implements Serializable {
    private static final long serialVersionUID = 3190472306106266938L;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCodeEnum currencyCodeEnum;

    @Column(name = "UNIT", nullable = false, length = 5)
    private String unit;

    public CurrencyCodeEnum getCurrencyCodeEnum() {
        return currencyCodeEnum;
    }

    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCodeEnum) {
        this.currencyCodeEnum = currencyCodeEnum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrProcessCtrlId)) {
            return false;
        }
        CurrProcessCtrlId that = (CurrProcessCtrlId) o;
        return currencyCodeEnum == that.currencyCodeEnum &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyCodeEnum, unit);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrProcessCtrlId{")
                .append("currencyCode = ").append(currencyCodeEnum)
                .append(",unit = '").append(unit).append('\'')
                .append("}");
        return sb.toString();
    }
}
