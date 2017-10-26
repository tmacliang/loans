package org.kelex.loans.core.service;

import org.kelex.loans.bean.RetailRequest;
import org.kelex.loans.core.SysintrException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/27.
 */
@Service
public class CycleService extends TransactionService<RetailRequest> {

    @Inject
    EntryService entryService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends RetailRequest> context) throws Exception {

    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RetailRequest> context) throws Exception {

    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RetailRequest> context) throws Exception {

    }

    public List next(AccountEntity account, CycleSummaryEntity cycle, IouReceiptEntity iou, LocalDate businessDate, TransactionContext context) {

        if (!Objects.equals(cycle.getId().getCycleNo(), iou.getCycleNo())) {
            throw new IllegalArgumentException("cycle.cycleNo != iou.cycleNo");
        }

        if (iou.isLastTerm()) {
            throw new RuntimeException("iou is last term");
        }

        RepositoryProxy repository = context.getRepository();

        String planId = iou.getPlanId();
        PlanProfileEntity planProfile = entryService.findOnePlanProfile(planId, repository);
        if (planProfile == null) {
            throw new SysintrException("plan profile is no such: " + planId);
        }

        StatusCodeEntity statusCode = entryService.findOneStatusCode(iou.getStatusCode(), planProfile.getStatusType(), repository);
        if (statusCode == null) {
            throw new RuntimeException("status code is null.");
        }
        if (!statusCode.getNextFlag()) {
            return null;
        }

        String productId = account.getProductId();
        String actTypeId = account.getActTypeId();

        PlanProcessCtrlEntity planCtrl = entryService.findOnePlanProcessCtrl(productId, actTypeId, planId, repository);
        if (planCtrl == null) {
            throw new RuntimeException("plan ctrl is null.");
        }

        iou.setCurrentTermNo(iou.getCurrentTermNo() + 1);
        iou.setOutstandingTerms(iou.getOutstandingTerms() - iou.getCurrentTermNo());

        BigDecimal currAmt;
        String txnCode;
        String nextCode = statusCode.getNextCode();

        if (iou.isFirstTerm()) {
            txnCode = planCtrl.getFirstTxnCode();
            currAmt = iou.getFirstTermAmt();
        } else if (iou.isFixedTerm()) {
            currAmt = iou.getLastTermAmt();
            txnCode = planCtrl.getFixedTxnCode();
        } else if (iou.isLastTerm()) {
            currAmt = iou.getFixedTermAmt();
            txnCode = planCtrl.getLastTxnCode();
            nextCode = statusCode.getLastCode();
        } else {
            throw new IllegalArgumentException("term error.");
        }

        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, txnCode, repository);
        if (txnCtrl == null) {
            throw new RuntimeException(txnCode + " txn ctrl is no such.");
        }

        IdWorker idWorker = context.getIdWorker();

        iou.setPvsStatusCode(iou.getStatusCode());
        iou.setCurrentBalance(iou.getCurrentBalance().add(currAmt));
        iou.setOutstandingTxnAmt(iou.getOutstandingTxnAmt().subtract(currAmt));
        iou.setStatusCode(nextCode);
        repository.save(iou);

        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(txnCode, repository);
        List<TxnSummaryEntity> txns = new ArrayList<TxnSummaryEntity>();

        TxnSummaryEntity txn = new TxnSummaryEntity();
        TxnSummaryId txnSummaryId = new TxnSummaryId();
        txnSummaryId.setAccountId(account.getAccountId());
        txnSummaryId.setCycleNo(cycle.getId().getCycleNo());
        txnSummaryId.setTxnSummaryNo(cycle.getNextTxnSummaryNo());
        txn.setId(txnSummaryId);
        txn.setTxnId(idWorker.nextId());
        txn.setIouId(iou.getIouId());
        txn.setOriginalTxnId(txn.getTxnId());
        txn.setCustomerId(account.getCustomerId());
        txn.setProductId(productId);
        txn.setActTypeId(actTypeId);
        txn.setTxnUuid(iou.getOrderNo());
        txn.setTermNo(iou.getCurrentTermNo());
        txn.setTxnCode(txnCode);
        txn.setTxnType(txnProfile.getTxnType());
        txn.setCustomerGenFlag(txnProfile.getCustomerGenFlag());
        txn.setGenTxnSummaryNo(0);
        txn.setMerchantName(iou.getMerchantName());
        txn.setOrderNo(iou.getOrderNo());
        txn.setFlowType(txnProfile.getFlowType());
        txn.setCurrencyCode(iou.getCurrencyCodeEnum());
        txn.setTxnAmt(currAmt);
        txn.setPostingAmt(currAmt);
        txn.setOutstandingDeductAmt(iou.getOutstandingTxnAmt());
        txn.setGenFeeAmt(BigDecimal.ZERO);
        txn.setReversalAmt(BigDecimal.ZERO);
        txn.setTxnDate(businessDate);
        txn.setTxnTime(LocalTime.now());
        repository.save(txn);
        txns.add(txn);

        cycle.setNextTxnSummaryNo(cycle.getNextTxnSummaryNo() + 1);

        TxnSummaryEntity fee = calculateTxnFee(iou, txn, txnCtrl, businessDate, context);
        if (fee != null) {
            fee.getId().setTxnSummaryNo(cycle.getNextTxnSummaryNo());
            iou.setPostingFeeAmt(iou.getPostingFeeAmt().add(fee.getTxnAmt()));
            txn.setGenFeeAmt(fee.getTxnAmt());
            cycle.setNextTxnSummaryNo(cycle.getNextTxnSummaryNo() + 1);
            repository.save(fee);
            txns.add(fee);
        }
        repository.save(cycle);
        return txns;
    }

    /**
     * 计算交易的手续费，返回的手续费对象不包含账期内交易号(使用{@link TxnSummaryEntity#getId()}的{@link TxnSummaryId#setTxnSummaryNo(Integer)}单独指定)。
     *
     * @param iou
     * @param txn
     * @param txnCtrl
     * @param businessDate
     * @param context
     * @return 无手续费返回 null
     */
    private TxnSummaryEntity calculateTxnFee(
            IouReceiptEntity iou
            , TxnSummaryEntity txn
            , TxnProcessCtrlEntity txnCtrl
            , LocalDate businessDate
            , TransactionContext context) {
        BigDecimal feeAmt = BigDecimal.ZERO;
        if (iou.isFirstTerm()) {
            feeAmt = iou.getFirstTermFeeAmt();
        } else if (iou.isFixedTerm()) {
            feeAmt = iou.getFixedTermFeeAmt();
        } else if (iou.isLastTerm()) {
            feeAmt = iou.getLastTermFeeAmt();
        }
        if (feeAmt.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        String feeCode = txnCtrl.getFeeCode();
        if (feeCode == null) {
            return null;
        }
        RepositoryProxy repository = context.getRepository();
        TxnProfileEntity feeProfile = entryService.findOneTxnProfile(feeCode, repository);
        if (feeProfile == null) {
            throw new SysintrException(feeCode + " txn profile is no such.");
        }

        IdWorker idWorker = context.getIdWorker();
        TxnSummaryId txnId = txn.getId();

        TxnSummaryId feeId = new TxnSummaryId();
        feeId.setAccountId(txnId.getAccountId());
        feeId.setCycleNo(txnId.getCycleNo());
        TxnSummaryEntity fee = new TxnSummaryEntity();
        fee.setId(feeId);
        fee.setOriginalTxnId(txn.getTxnId());
        fee.setCustomerId(txn.getCustomerId());
        fee.setIouId(txn.getIouId());
        fee.setTxnId(idWorker.nextId());
        fee.setProductId(txn.getProductId());
        fee.setActTypeId(txn.getActTypeId());
        fee.setTxnType(feeProfile.getTxnType());
        fee.setTxnCode(feeCode);
        fee.setTxnAmt(feeAmt);
        fee.setPostingAmt(feeAmt);
        fee.setTermNo(txn.getTermNo());
        fee.setGenFeeAmt(BigDecimal.ZERO);
        fee.setGenTxnSummaryNo(txnId.getTxnSummaryNo());
        fee.setReversalAmt(BigDecimal.ZERO);
        fee.setCurrencyCode(txn.getCurrencyCode());
        fee.setFlowType(feeProfile.getFlowType());
        fee.setCustomerGenFlag(feeProfile.getCustomerGenFlag());
        fee.setTxnDate(businessDate);
        fee.setTxnTime(LocalTime.now());
        fee.setOutstandingDeductAmt(BigDecimal.ZERO);
        return fee;
    }
}
