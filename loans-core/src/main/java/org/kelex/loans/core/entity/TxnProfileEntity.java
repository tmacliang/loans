package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;
import org.kelex.loans.core.enumeration.FlowType;

import javax.persistence.*;

/**
 * Created by hechao on 2017/9/28.
 */
@Entity
@Table(name = "txn_profile")
public class TxnProfileEntity extends DescriptionEntity {

    private static final long serialVersionUID = 2321156111864596300L;

    @Id
    @Column(name = "TXN_CODE", nullable = false, length = 4)
    private String txnCode;

    @Column(name = "TXN_TYPE", nullable = false, length = 4)
    private String txnType;

    @Column(name = "STATISTICS_CODE", nullable = false, length = 4)
    private String statisticsCode;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "FLOW_TYPE", nullable = false, length = 1)
    private FlowType flowType;

    @Column(name = "REVERSAL_TXN_CODE", nullable = true, length = 4)
    private String reversalTxnCode;

    @Type(type = "yes_no")
    @Column(name = "CUSTOMER_GEN_FLAG", nullable = false, length = 1)
    private Boolean customerGenFlag;

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getStatisticsCode() {
        return statisticsCode;
    }

    public void setStatisticsCode(String statisticsCode) {
        this.statisticsCode = statisticsCode;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public String getReversalTxnCode() {
        return reversalTxnCode;
    }

    public void setReversalTxnCode(String reversalTxnCode) {
        this.reversalTxnCode = reversalTxnCode;
    }

    public Boolean getCustomerGenFlag() {
        return customerGenFlag;
    }

    public void setCustomerGenFlag(Boolean customerGenFlag) {
        this.customerGenFlag = customerGenFlag;
    }

    @Override
    public Object primaryKey() {
        return txnCode;
    }

    public boolean isDebitFlow(){
        return flowType == FlowType.D;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TxnProfileEntity{")
                .append("customerGenFlag = ").append(customerGenFlag)
                .append(",flowType = ").append(flowType)
                .append(",reversalTxnCode = '").append(reversalTxnCode).append('\'')
                .append(",statisticsCode = '").append(statisticsCode).append('\'')
                .append(",txnCode = '").append(txnCode).append('\'')
                .append(",txnType = '").append(txnType).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
