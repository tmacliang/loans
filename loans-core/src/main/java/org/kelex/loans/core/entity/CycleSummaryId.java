package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/14.
 */
@Embeddable
public class CycleSummaryId implements Serializable {

    private static final long serialVersionUID = 325559907369970800L;

    @Column(name = "ACCOUNT_ID", nullable = false, updatable = false)
    private Long accountId;

    @Column(name = "CYCLE_NO", nullable = false, updatable = false)
    private Integer cycleNo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CycleSummaryId)) {
            return false;
        }
        CycleSummaryId that = (CycleSummaryId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(cycleNo, that.cycleNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, cycleNo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CycleSummaryId{")
                .append("accountId = ").append(accountId)
                .append(",cycleNo = ").append(cycleNo)
                .append("}");
        return sb.toString();
    }
}
