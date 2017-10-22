package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/10/17.
 */
@Embeddable
public class BalCompValId implements Serializable {

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Long accountId;

    @Column(name = "CYCLE_NO", nullable = false)
    private Integer cycleNo;

    @Column(name = "BCP_ID", nullable = false, length = 4)
    private String bcpId;

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

    public String getBcpId() {
        return bcpId;
    }

    public void setBcpId(String bcpId) {
        this.bcpId = bcpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BalCompValId)){
            return false;
        }
        BalCompValId that = (BalCompValId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(cycleNo, that.cycleNo) &&
                Objects.equals(bcpId, that.bcpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, cycleNo, bcpId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalCompValId{")
                .append("accountId = ").append(accountId)
                .append(",bcpId = '").append(bcpId).append('\'')
                .append(",cycleNo = ").append(cycleNo)
                .append("}");
        return sb.toString();
    }
}
