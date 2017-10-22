package org.kelex.loans.core.entity;

import org.kelex.loans.core.enumeration.FlowType;

import javax.persistence.*;

/**
 * Created by hechao on 2017/10/17.
 */
@Entity
@Table(name = "bal_comp_profile")
public class BalCompProfileEntity extends DescriptionEntity{
    @Id
    @Column(name = "BCP_ID", nullable = false, length = 4)
    private String bcpId;

    @Column(name = "BAL_TYPE", nullable = false, length = 4)
    private String balType;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "FLOW_TYPE", nullable = false, length = 1)
    private FlowType flowType;

    public String getBcpId() {
        return bcpId;
    }

    public void setBcpId(String bcpId) {
        this.bcpId = bcpId;
    }

    public String getBalType() {
        return balType;
    }

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

    @Override
    public Object primaryKey() {
        return bcpId;
    }

    public boolean isDebitFlow(){
        return flowType == FlowType.D;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalCompProfileEntity{")
                .append("balType = '").append(balType).append('\'')
                .append(",bcpId = '").append(bcpId).append('\'')
                .append(",flowType = ").append(flowType)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
