package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hechao on 2017/10/17.
 */
@Entity
@Table(name = "bal_process_ctrl")
public class BalProcessCtrlEntity extends  DescriptionEntity implements Serializable{

    private static final long serialVersionUID = -2741825187885991790L;

    @EmbeddedId
    private BalProcessCtrlId id;

    @Basic
    @Type(type = "yes_no")
    @Column(name = "WAIVE_INTEREST_FLAG", nullable = false, length = 1)
    private Boolean waiveInterestFlag;

    @Basic
    @Column(name = "INTEREST_TXN_CODE", nullable = true, length = 4)
    private String interestTxnCode;

    /**
     * 得到余额成分控制ID
     * @return
     */
    public BalProcessCtrlId getId() {
        return id;
    }

    /**
     * 设置余额成分控制ID
     * @param id
     */
    public void setId(BalProcessCtrlId id) {
        this.id = id;
    }

    /**
     * 得到免息标识
     * @return
     */
    public Boolean getWaiveInterestFlag() {
        return waiveInterestFlag;
    }

    /**
     * 设置免息标识
     * @param waiveInterestFlag
     */
    public void setWaiveInterestFlag(Boolean waiveInterestFlag) {
        this.waiveInterestFlag = waiveInterestFlag;
    }

    /**
     * 得到利息代码
     * @return
     */
    public String getInterestTxnCode() {
        return interestTxnCode;
    }

    /**
     * 设置利息代码
     * @param interestTxnCode
     */
    public void setInterestTxnCode(String interestTxnCode) {
        this.interestTxnCode = interestTxnCode;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalProcessCtrlEntity{")
                .append("id = ").append(id)
                .append(",interestTxnCode = '").append(interestTxnCode).append('\'')
                .append(",waiveInterestFlag = ").append(waiveInterestFlag)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
