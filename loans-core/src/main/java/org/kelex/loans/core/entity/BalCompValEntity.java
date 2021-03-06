package org.kelex.loans.core.entity;

import org.kelex.loans.core.enumeration.FlowType;
import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.*;
import javax.persistence.EnumType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by hechao on 2017/10/17.
 */
@Entity
@Table(name = "bal_comp_val")
public class BalCompValEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 3721388137671384884L;

    @EmbeddedId
    private BalCompValId id;

    @Basic
    @Column(name = "BAL_TYPE", nullable = false, length = 4)
    private String balType;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "FLOW_TYPE", nullable = false, length = 1)
    private FlowType flowType;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 5)
    private CurrencyCodeEnum currencyCodeEnum;

    @Basic
    @Column(name = "CTD_BALANCE", nullable = false, precision = 2)
    private BigDecimal ctdBalance;

    @Basic
    @Column(name = "PVS_BALANCE", nullable = false, precision = 2)
    private BigDecimal pvsBalance;

    @Basic
    @Column(name = "OLD_BALANCE", nullable = false, precision = 2)
    private BigDecimal oldBalance;

    @Basic
    @Column(name = "PVS_INTEREST_VAL", nullable = false, precision = 2)
    private BigDecimal pvsInterestVal;

    @Basic
    @Column(name = "OLD_INTEREST_VAL", nullable = false, precision = 2)
    private BigDecimal oldInterestVal;

    @NotNull
    @Column(name = "accrued_through_date", nullable = false)
    private LocalDate accruedThroughDate;

    /**
     * 得到余额成分ID
     * @return
     */
    public BalCompValId getId() {
        return id;
    }

    /**
     * 设置余额成分ID
     * @param id
     */
    public void setId(BalCompValId id) {
        this.id = id;
    }

    /**
     * 得到余额成分类型
     * @return
     */
    public String getBalType() {
        return balType;
    }

    /**
     * 设置余额成分类型
     * @param balType
     */
    public void setBalType(String balType) {
        this.balType = balType;
    }

    /**
     * 得到资金流向类型
     * @return
     */
    public FlowType getFlowType() {
        return flowType;
    }

    /**
     * 设置资金流向类型
     * @param flowType
     */
    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    /**
     * 得到币种
     * @return
     */
    public CurrencyCodeEnum getCurrencyCodeEnum() {
        return currencyCodeEnum;
    }

    /**
     * 设置币种
     * @param currencyCodeEnum
     */
    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCodeEnum) {
        this.currencyCodeEnum = currencyCodeEnum;
    }

    /**
     * 得到当期余额
     * @return
     */
    public BigDecimal getCtdBalance() {
        return ctdBalance;
    }

    /**
     * 设置当期余额
     * @param ctdBalance
     */
    public void setCtdBalance(BigDecimal ctdBalance) {
        this.ctdBalance = ctdBalance;
    }

    /**
     * 得到上期余额
     * @return
     */
    public BigDecimal getPvsBalance() {
        return pvsBalance;
    }

    /**
     * 设置上期余额
     * @param pvsBalance
     */
    public void setPvsBalance(BigDecimal pvsBalance) {
        this.pvsBalance = pvsBalance;
    }

    /**
     * 得到上期前余额
     * @return
     */
    public BigDecimal getOldBalance() {
        return oldBalance;
    }

    /**
     * 设置上期前余额
     * @param oldBalance
     */
    public void setOldBalance(BigDecimal oldBalance) {
        this.oldBalance = oldBalance;
    }

    public BigDecimal getPvsInterestVal() {
        return pvsInterestVal;
    }

    public void setPvsInterestVal(BigDecimal pvsInterestVal) {
        this.pvsInterestVal = pvsInterestVal;
    }

    public BigDecimal getOldInterestVal() {
        return oldInterestVal;
    }

    public void setOldInterestVal(BigDecimal oldInterestVal) {
        this.oldInterestVal = oldInterestVal;
    }

    public LocalDate getAccruedThroughDate() {
        return accruedThroughDate;
    }

    public void setAccruedThroughDate(LocalDate accruedThroughDate) {
        this.accruedThroughDate = accruedThroughDate;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalCompValEntity{")
                .append("balType = '").append(balType).append('\'')
                .append(",ctdBalance = ").append(ctdBalance)
                .append(",currencyCode = ").append(currencyCodeEnum)
                .append(",flowType = ").append(flowType)
                .append(",id = ").append(id)
                .append(",oldBalance = ").append(oldBalance)
                .append(",pvsBalance = ").append(pvsBalance)
                .append(",pvsInterestVal = ").append(pvsInterestVal)
                .append(",oldInterestVal = ").append(oldInterestVal)
                .append(",accruedThroughDate = ").append(accruedThroughDate)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
