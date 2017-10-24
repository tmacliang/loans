package org.kelex.loans.core.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ActCreditHistory.
 */
@Entity
@Table(name = "act_credit_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActCreditHistoryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @EmbeddedId
    private ActCreditHistoryId id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "credit_limit_before", precision = 17, scale = 2, nullable = false)
    private BigDecimal creditLimitBefore;

    @NotNull
    @Column(name = "credit_limit_after", precision = 17, scale = 2, nullable = false)
    private BigDecimal creditLimitAfter;

    @NotNull
    @Column(name = "available_balance_before", precision = 17, scale = 2, nullable = false)
    private BigDecimal availableBalanceBefore;

    @NotNull
    @Column(name = "available_balance_after", precision = 17, scale = 2, nullable = false)
    private BigDecimal availableBalanceAfter;


    public ActCreditHistoryId getId() {
        return id;
    }

    public ActCreditHistoryEntity id(ActCreditHistoryId id) {
        this.id = id;
        return this;
    }

    public void setId(ActCreditHistoryId id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ActCreditHistoryEntity startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ActCreditHistoryEntity endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCreditLimitBefore() {
        return creditLimitBefore;
    }

    public ActCreditHistoryEntity creditLimitBefore(BigDecimal creditLimitBefore) {
        this.creditLimitBefore = creditLimitBefore;
        return this;
    }

    public void setCreditLimitBefore(BigDecimal creditLimitBefore) {
        this.creditLimitBefore = creditLimitBefore;
    }

    public BigDecimal getCreditLimitAfter() {
        return creditLimitAfter;
    }

    public ActCreditHistoryEntity creditLimitAfter(BigDecimal creditLimitAfter) {
        this.creditLimitAfter = creditLimitAfter;
        return this;
    }

    public void setCreditLimitAfter(BigDecimal creditLimitAfter) {
        this.creditLimitAfter = creditLimitAfter;
    }

    public BigDecimal getAvailableBalanceBefore() {
        return availableBalanceBefore;
    }

    public ActCreditHistoryEntity availableBalanceBefore(BigDecimal availableBalanceBefore) {
        this.availableBalanceBefore = availableBalanceBefore;
        return this;
    }

    public void setAvailableBalanceBefore(BigDecimal availableBalanceBefore) {
        this.availableBalanceBefore = availableBalanceBefore;
    }

    public BigDecimal getAvailableBalanceAfter() {
        return availableBalanceAfter;
    }

    public ActCreditHistoryEntity availableBalanceAfter(BigDecimal availableBalanceAfter) {
        this.availableBalanceAfter = availableBalanceAfter;
        return this;
    }

    public void setAvailableBalanceAfter(BigDecimal availableBalanceAfter) {
        this.availableBalanceAfter = availableBalanceAfter;
    }

    @Override
    public String primaryKey() {
        return id.getAccountId() + ":" + id.getLimitChangeDate() + ":" + id.getLimitChangeTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActCreditHistoryEntity actCreditHistoryEntity = (ActCreditHistoryEntity) o;
        if (actCreditHistoryEntity.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, actCreditHistoryEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ActCreditHistory{" +
                "id=" + id +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", creditLimitBefore='" + creditLimitBefore + "'" +
                ", creditLimitAfter='" + creditLimitAfter + "'" +
                ", availableBalanceBefore='" + availableBalanceBefore + "'" +
                ", availableBalanceAfter='" + availableBalanceAfter + "'" +
                '}';
    }
}
