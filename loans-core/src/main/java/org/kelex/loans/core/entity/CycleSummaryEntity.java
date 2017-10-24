package org.kelex.loans.core.entity;

import org.kelex.loans.enumeration.CurrencyCodeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by hechao on 2017/9/14.
 */
@Entity
@Table(name = "cycle_summary")
public class CycleSummaryEntity extends BaseEntity{

    private static final long serialVersionUID = -4401629532070134424L;

    @Id
    @EmbeddedId
    @NotNull
    private CycleSummaryId id;

    @Column(name = "TOTAL_CREDITS", nullable = false)
    private Integer totalCredits;

    @Column(name = "TOTAL_DEBITS", nullable = false)
    private Integer totalDebits;

    @Column(name = "TOTAL_TXNS", nullable = false)
    private Integer totalTxns;

    @Column(name = "NEXT_TXN_SUMMARY_NO", nullable = false)
    private Integer nextTxnSummaryNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_CODE", nullable = false, length = 4)
    private CurrencyCodeEnum currencyCodeEnum;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "CYCLE_DATE", nullable = true)
    private LocalDate cycleDate;

    @Column(name = "OPEN_DUE_DATE", nullable = false)
    private LocalDate openDueDate;

    @Column(name = "OPEN_TOTAL_DUE", nullable = false, precision = 2)
    private BigDecimal openTotalDue;

    @Column(name = "OPEN_BALANCE", nullable = false, precision = 2)
    private BigDecimal openBalance;

    @Column(name = "CLOSE_BALANCE", nullable = false, precision = 2)
    private BigDecimal closeBalance;

    @Column(name = "TOTAL_PAYMENT_AMT", nullable = false, precision = 2)
    private BigDecimal totalPaymentAmt;

    @Column(name = "TOTAL_CYCLE_AMT", nullable = false, precision = 2)
    private BigDecimal totalCycleAmt;

    @Column(name = "TOTAL_CREDIT_AMT", nullable = false, precision = 2)
    private BigDecimal totalCreditAmt;

    @Column(name = "TOTAL_DEBIT_AMT", nullable = false, precision = 2)
    private BigDecimal totalDebitAmt;

    @Column(name = "TOTAL_GRACE_AMT", nullable = false, precision = 2)
    private BigDecimal totalGraceAmt;

    @Column(name = "TOTAL_GRACE_CREDIT_AMT", nullable = false, precision = 2)
    private BigDecimal totalGraceCreditAmt;

    @Column(name = "TOTAL_GRACE_DEBIT_AMT", nullable = false, precision = 2)
    private BigDecimal totalGraceDebitAmt;

    public CycleSummaryId getId() {
        return id;
    }

    public void setId(CycleSummaryId id) {
        this.id = id;
    }

    public Integer getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Integer totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Integer getTotalDebits() {
        return totalDebits;
    }

    public void setTotalDebits(Integer totalDebits) {
        this.totalDebits = totalDebits;
    }

    public Integer getTotalTxns() {
        return totalTxns;
    }

    public void setTotalTxns(Integer totalTxns) {
        this.totalTxns = totalTxns;
    }

    public Integer getNextTxnSummaryNo() {
        return nextTxnSummaryNo;
    }

    public void setNextTxnSummaryNo(Integer nextTxnSummaryNo) {
        this.nextTxnSummaryNo = nextTxnSummaryNo;
    }

    public CurrencyCodeEnum getCurrencyCodeEnum() {
        return currencyCodeEnum;
    }

    public void setCurrencyCodeEnum(CurrencyCodeEnum currencyCodeEnum) {
        this.currencyCodeEnum = currencyCodeEnum;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCycleDate() {
        return cycleDate;
    }

    public void setCycleDate(LocalDate cycleDate) {
        this.cycleDate = cycleDate;
    }

    public LocalDate getOpenDueDate() {
        return openDueDate;
    }

    public void setOpenDueDate(LocalDate openDueDate) {
        this.openDueDate = openDueDate;
    }

    public BigDecimal getOpenTotalDue() {
        return openTotalDue;
    }

    public void setOpenTotalDue(BigDecimal openTotalDue) {
        this.openTotalDue = openTotalDue;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getCloseBalance() {
        return closeBalance;
    }

    public void setCloseBalance(BigDecimal closeBalance) {
        this.closeBalance = closeBalance;
    }

    public BigDecimal getTotalPaymentAmt() {
        return totalPaymentAmt;
    }

    public void setTotalPaymentAmt(BigDecimal totalPaymentAmt) {
        this.totalPaymentAmt = totalPaymentAmt;
    }

    public BigDecimal getTotalCycleAmt() {
        return totalCycleAmt;
    }

    public void setTotalCycleAmt(BigDecimal totalCycleAmt) {
        this.totalCycleAmt = totalCycleAmt;
    }

    public BigDecimal getTotalCreditAmt() {
        return totalCreditAmt;
    }

    public void setTotalCreditAmt(BigDecimal totalCreditAmt) {
        this.totalCreditAmt = totalCreditAmt;
    }

    public BigDecimal getTotalDebitAmt() {
        return totalDebitAmt;
    }

    public void setTotalDebitAmt(BigDecimal totalDebitAmt) {
        this.totalDebitAmt = totalDebitAmt;
    }

    public BigDecimal getTotalGraceAmt() {
        return totalGraceAmt;
    }

    public void setTotalGraceAmt(BigDecimal totalGraceAmt) {
        this.totalGraceAmt = totalGraceAmt;
    }

    public BigDecimal getTotalGraceCreditAmt() {
        return totalGraceCreditAmt;
    }

    public void setTotalGraceCreditAmt(BigDecimal totalGraceCreditAmt) {
        this.totalGraceCreditAmt = totalGraceCreditAmt;
    }

    public BigDecimal getTotalGraceDebitAmt() {
        return totalGraceDebitAmt;
    }

    public void setTotalGraceDebitAmt(BigDecimal totalGraceDebitAmt) {
        this.totalGraceDebitAmt = totalGraceDebitAmt;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CycleSummaryEntity{")
                .append("closeBalance = ").append(closeBalance)
                .append(",currencyCode = ").append(currencyCodeEnum)
                .append(",cycleDate = ").append(cycleDate)
                .append(",endDate = ").append(endDate)
                .append(",id = ").append(id)
                .append(",nextTxnSummaryNo = ").append(nextTxnSummaryNo)
                .append(",openBalance = ").append(openBalance)
                .append(",openDueDate = ").append(openDueDate)
                .append(",openTotalDue = ").append(openTotalDue)
                .append(",startDate = ").append(startDate)
                .append(",totalCreditAmt = ").append(totalCreditAmt)
                .append(",totalCredits = ").append(totalCredits)
                .append(",totalCycleAmt = ").append(totalCycleAmt)
                .append(",totalDebitAmt = ").append(totalDebitAmt)
                .append(",totalDebits = ").append(totalDebits)
                .append(",totalGraceAmt = ").append(totalGraceAmt)
                .append(",totalGraceCreditAmt = ").append(totalGraceCreditAmt)
                .append(",totalGraceDebitAmt = ").append(totalGraceDebitAmt)
                .append(",totalPaymentAmt = ").append(totalPaymentAmt)
                .append(",totalTxns = ").append(totalTxns)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
