package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;


@Embeddable
public class ActCreditHistoryId implements Serializable {

    @NotNull
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @NotNull
    @Size(max = 4)
    @Column(name = "ccd_id", length = 4, nullable = false)
    private String ccdId;

    @NotNull
    @Column(name = "limit_change_date", nullable = false)
    private LocalDate limitChangeDate;

    @NotNull
    @Column(name = "limit_change_time", nullable = false)
    private LocalTime limitChangeTime;

    public Long getAccountId() {
        return accountId;
    }

    public ActCreditHistoryId accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCcdId() {
        return ccdId;
    }

    public ActCreditHistoryId ccdId(String ccdId) {
        this.ccdId = ccdId;
        return this;
    }

    public void setCcdId(String ccdId) {
        this.ccdId = ccdId;
    }

    public LocalDate getLimitChangeDate() {
        return limitChangeDate;
    }

    public ActCreditHistoryId limitChangeDate(LocalDate limitChangeDate) {
        this.limitChangeDate = limitChangeDate;
        return this;
    }

    public void setLimitChangeDate(LocalDate limitChangeDate) {
        this.limitChangeDate = limitChangeDate;
    }

    public LocalTime getLimitChangeTime() {
        return limitChangeTime;
    }

    public ActCreditHistoryId limitChangeTime(LocalTime limitChangeTime) {
        this.limitChangeTime = limitChangeTime;
        return this;
    }

    public void setLimitChangeTime(LocalTime limitChangeTime) {
        this.limitChangeTime = limitChangeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActCreditHistoryId that = (ActCreditHistoryId) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(ccdId, that.ccdId) &&
                Objects.equals(limitChangeDate, that.limitChangeDate) &&
                Objects.equals(limitChangeTime, that.limitChangeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, ccdId, limitChangeDate, limitChangeTime);
    }

    @Override
    public String toString() {
        return "[" +
                "accountId=" + accountId +
                "ccdId=" + ccdId +
                ", limitChangeDate=" + limitChangeDate +
                ", limitChangeTime=" + limitChangeTime +
                ']';
    }
}
