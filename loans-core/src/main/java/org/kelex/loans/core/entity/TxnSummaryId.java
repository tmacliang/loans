package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/10/9.
 */
@Embeddable
public class TxnSummaryId implements Serializable {

    private static final long serialVersionUID = -5009822169002994249L;

    @Column(name = "ACCOUNT_ID", nullable = false, updatable = false)
    private Long accountId;

    @Column(name = "CYCLE_NO", nullable = false)
    private Integer cycleNo;

    @Column(name = "TXN_SUMMARY_NO", nullable = false)
    private Integer txnSummaryNo;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getCycleNo() {
        return cycleNo;
    }

    public void setCycleNo(Integer cycleNo) {
        this.cycleNo = cycleNo;
    }

    public Integer getTxnSummaryNo() {
        return txnSummaryNo;
    }

    public void setTxnSummaryNo(Integer txnSummaryNo) {
        this.txnSummaryNo = txnSummaryNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TxnSummaryId)) {
            return false;
        }
        TxnSummaryId that = (TxnSummaryId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(cycleNo, that.cycleNo) &&
                Objects.equals(txnSummaryNo, that.txnSummaryNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, cycleNo, txnSummaryNo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TxnSummaryId{")
                .append("accountId = ").append(accountId)
                .append(",cycleNo = ").append(cycleNo)
                .append(",txnSummaryNo = ").append(txnSummaryNo)
                .append("}");
        return sb.toString();
    }
}
