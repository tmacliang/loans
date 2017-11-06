package org.kelex.loans.core.service;

import org.hibernate.Transaction;
import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.RefundRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.core.util.IdWorker;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

/**
 * Created by licl1 on 2017/11/2.
 */
public class RefundService extends TransactionService<RefundRequest> {

    @Inject
    private EntryService entryService;

    @Inject
    private TxnSummaryRepository txnSummaryRepository;

    @Inject
    private IouReceiptRepository iouReceiptRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private CycleSummaryRepository cycleSummaryRepository;



    @Override
    protected void checkArguments(TransactionRequestContext<? extends RefundRequest> context) throws Exception {
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();
        AssertUtils.notNull(data.getIouId(), ArgumentMessageEnum.ERROR_IOUID_ISNULL);
        if (data.getRefundAmount().compareTo(ZERO) <= 0) {
            throw new ServerRuntimeException(500, "refund amount must more than 0");
        }
        AssertUtils.notNull(data.getRefundOrderNo(), ArgumentMessageEnum.ERROR_REFUNDORDERNO_ISNULL);
        AssertUtils.notNull(data.getCurrencyCode(), ArgumentMessageEnum.ERROR_CURRENCYCODE_ISNULL);
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RefundRequest> context) throws Exception {
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();

        //判断订单是否存在
        Optional<TxnSummaryEntity> txnSummaryOpt = txnSummaryRepository.findOneByOrderNo(data.getRefundOrderNo());
        if (txnSummaryOpt.isPresent()) {
            throw new ServerRuntimeException(500, "txnSummary not exist");
        }
        IouReceiptEntity iouReceipt = iouReceiptRepository.findOne(data.getIouId());
        //判断金额是否不大于可退金额
        BigDecimal refundAmount = data.getRefundAmount();
        BigDecimal availableRefundAmount = iouReceipt.getIouAmt().subtract(iouReceipt.getTotalReversalAmt());
        if (refundAmount.compareTo(availableRefundAmount) > 0) {
            throw new ServerRuntimeException(500, "refundAmount is too large");
        }
        //TODO 用户状态码非DECL
        AccountEntity account = accountRepository.findOne(iouReceipt.getAccountId());
        CycleSummaryId cycleSummaryId = new CycleSummaryId();
        cycleSummaryId.setCycleNo(iouReceipt.getCycleNo());
        cycleSummaryId.setAccountId(account.getAccountId());
        CycleSummaryEntity summaryEntity = cycleSummaryRepository.findOne(cycleSummaryId);

        context.setAttribute(CycleSummaryEntity.class, summaryEntity);
        context.setAttribute(AccountEntity.class, account);
        context.setAttribute(IouReceiptEntity.class, iouReceipt);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RefundRequest> context) throws Exception {

        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();
        IouReceiptEntity iouReceipt = (IouReceiptEntity)context.getAttribute(IouReceiptEntity.class);

        BigDecimal refundAmount = data.getRefundAmount();
        BigDecimal availableRefundAmount = iouReceipt.getIouAmt().subtract(iouReceipt.getTotalReversalAmt());
        BigDecimal refundFeeAmt;

        if (refundAmount.compareTo(availableRefundAmount) == 0) {
            refundFeeAmt = iouReceipt.getTotalFeeAmt().subtract(iouReceipt.getTotalReversalFeeAmt());
        }else{
            refundFeeAmt = iouReceipt.getTotalFeeAmt().multiply(refundAmount.divide(iouReceipt.getIouAmt())).setScale(2, RoundingMode.HALF_UP);
        }
        if(iouReceipt.getTotalReversalAmt().compareTo(ZERO) > 0 && refundFeeAmt.compareTo(ZERO) < 0){
            refundFeeAmt = new BigDecimal("0.01");
        }
        if(iouReceipt.getTotalReversalFeeAmt().add(refundFeeAmt).compareTo(iouReceipt.getTotalFeeAmt()) > 0){
            refundFeeAmt = iouReceipt.getTotalFeeAmt().subtract(iouReceipt.getTotalReversalFeeAmt());
        }

        BigDecimal osgDeductAmt,postingAmt;
        BigDecimal outstandingTxnAmt = iouReceipt.getOutstandingTxnAmt();
        if(refundAmount.compareTo(outstandingTxnAmt) > 0){
            osgDeductAmt = outstandingTxnAmt;
            postingAmt=refundAmount.subtract(outstandingTxnAmt);
            outstandingTxnAmt = ZERO;
        }else{
            osgDeductAmt = outstandingTxnAmt.subtract(refundAmount);
            postingAmt = ZERO;
            outstandingTxnAmt = outstandingTxnAmt.subtract(refundAmount);
        }

        BigDecimal osgDeductFeeAmt,postingFeeAmt;
        if(refundAmount.compareTo(outstandingTxnAmt) > 0){
            osgDeductFeeAmt = outstandingTxnAmt;
            postingFeeAmt=refundAmount.subtract(outstandingTxnAmt);
            outstandingTxnAmt = ZERO;
        }else{
            osgDeductFeeAmt = outstandingTxnAmt.subtract(refundAmount);
            postingFeeAmt = ZERO;
            outstandingTxnAmt = outstandingTxnAmt.subtract(refundAmount);
        }

        //更新白条数据
        iouReceipt.setOutstandingTxnAmt(outstandingTxnAmt);
        iouReceipt.setTotalReversalAmt(iouReceipt.getTotalReversalAmt().add(refundAmount));
        iouReceipt.setTotalReversalFeeAmt(iouReceipt.getTotalReversalFeeAmt().add(refundFeeAmt));
        iouReceipt.setLastReversalDate(request.getBusinessDate());
        iouReceipt.setTotalReversal(iouReceipt.getTotalReversal() + 1);
        if(iouReceipt.getOutstandingTxnAmt().compareTo(ZERO) == 0){
            iouReceipt.setPvsStatusCode(iouReceipt.getStatusCode());
            if(iouReceipt.getTotalReversalAmt().compareTo(iouReceipt.getIouAmt()) == 0){
                iouReceipt.setStatusCode("REVS");
            }else{
                iouReceipt.setStatusCode("COMP");
            }
        }

        TxnSummaryEntity refundAmtTxn =  createRefundAmtTxn(context);
        refundAmtTxn.setPostingAmt(postingAmt);
        refundAmtTxn.setTxnAmt(refundAmount);
        refundAmtTxn.setGenFeeAmt(refundFeeAmt);
        refundAmtTxn.setOutstandingDeductAmt(osgDeductAmt);

        TxnSummaryEntity refundFeeTxn = createRefundFeeTxn(context);
        refundFeeTxn.setPostingAmt(postingFeeAmt);
        refundFeeTxn.setOutstandingDeductAmt(osgDeductFeeAmt);
        refundFeeTxn.setTxnAmt(refundFeeAmt);

        RepositoryProxy repositoryProxy = context.getRepository();
        repositoryProxy.save(refundAmtTxn);
        repositoryProxy.save(refundFeeTxn);
    }

    /**
     * 创建退本金交易
     * @param context
     * @return
     */
    public TxnSummaryEntity createRefundAmtTxn(TransactionRequestContext<? extends RefundRequest> context){
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();
        RepositoryProxy repository = context.getRepository();
        AccountEntity account = (AccountEntity)context.getAttribute(AccountEntity.class);
        CycleSummaryEntity cycleSummary = (CycleSummaryEntity)context.getAttribute(CycleSummaryEntity.class);
        IouReceiptEntity iouReceipt = (IouReceiptEntity)context.getAttribute(IouReceiptEntity.class);

        PlanProcessCtrlEntity planProcessCtrl = entryService.findOnePlanProcessCtrl(account.getProductId(), account.getActTypeId(), iouReceipt.getPlanId());
        String txnCode = planProcessCtrl.getFirstTxnCode();

        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(txnCode, repository);
        String reverseCode = txnProfile.getReversalTxnCode();
        cycleSummary.setNextTxnSummaryNo(cycleSummary.getNextTxnSummaryNo() + 1);

        IdWorker idWorker = context.getIdWorker();

        TxnSummaryId id = new TxnSummaryId();
        id.setAccountId(account.getAccountId());
        id.setTxnSummaryNo(account.getCurrentCycleNo());
        id.setTxnSummaryNo(cycleSummary.getNextTxnSummaryNo());

        TxnSummaryEntity refundTxn = new TxnSummaryEntity();
        refundTxn.setId(id);
        refundTxn.setCustomerId(account.getCustomerId());
        refundTxn.setProductId(account.getProductId());
        refundTxn.setGenTxnSummaryNo(0);
        refundTxn.setIouId(iouReceipt.getIouId());
        refundTxn.setTermNo(iouReceipt.getCurrentTermNo());
        refundTxn.setTxnCode(reverseCode);
        refundTxn.setTxnType(txnProfile.getTxnType());
        refundTxn.setOrderNo(data.getRefundOrderNo());
        refundTxn.setTxnUuid(data.getRefundOrderNo());
        refundTxn.setTxnId(idWorker.nextId());
        refundTxn.setCurrencyCode(account.getCurrencyCode());
        refundTxn.setTxnAmt(iouReceipt.getIouAmt());//原始交易金额
        refundTxn.setCustomerGenFlag(false);
        refundTxn.setFlowType(txnProfile.getFlowType());
        refundTxn.setTxnDate(request.getBusinessDate());
        refundTxn.setTxnTime(LocalTime.now());

        repository.save(refundTxn);
        return refundTxn;
    }

    /**
     * 创建退费交易
     * @param context
     * @return
     */
    public TxnSummaryEntity createRefundFeeTxn(TransactionRequestContext<? extends RefundRequest> context){
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();
        RepositoryProxy repository = context.getRepository();
        AccountEntity account = (AccountEntity)context.getAttribute(AccountEntity.class);
        CycleSummaryEntity cycleSummary = (CycleSummaryEntity)context.getAttribute(CycleSummaryEntity.class);
        IouReceiptEntity iouReceipt = (IouReceiptEntity)context.getAttribute(IouReceiptEntity.class);

        PlanProcessCtrlEntity planProcessCtrl = entryService.findOnePlanProcessCtrl(account.getProductId(), account.getActTypeId(), iouReceipt.getPlanId());
        String txnCode = planProcessCtrl.getFirstTxnCode();

        TxnProcessCtrlEntity txnProcessCtrlEntity = entryService.findOneTxnProcessCtrl(account.getProductId(), account.getActTypeId(), txnCode, repository);
        String feeCode = txnProcessCtrlEntity.getFeeCode();

        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(feeCode, repository);
        String reverseCode = txnProfile.getReversalTxnCode();
        cycleSummary.setNextTxnSummaryNo(cycleSummary.getNextTxnSummaryNo() + 1);

        IdWorker idWorker = context.getIdWorker();

        TxnSummaryId id = new TxnSummaryId();
        id.setAccountId(account.getAccountId());
        id.setTxnSummaryNo(account.getCurrentCycleNo());
        id.setTxnSummaryNo(cycleSummary.getNextTxnSummaryNo() - 2);

        TxnSummaryEntity refundFeeTxn = new TxnSummaryEntity();
        refundFeeTxn.setId(id);
        refundFeeTxn.setCustomerId(account.getCustomerId());
        refundFeeTxn.setProductId(account.getProductId());
        refundFeeTxn.setGenTxnSummaryNo(0);
        refundFeeTxn.setIouId(iouReceipt.getIouId());
        refundFeeTxn.setTermNo(iouReceipt.getCurrentTermNo());
        refundFeeTxn.setTxnCode(reverseCode);
        refundFeeTxn.setTxnType(txnProfile.getTxnType());
        refundFeeTxn.setOrderNo(data.getRefundOrderNo());
        refundFeeTxn.setTxnUuid(data.getRefundOrderNo());
        refundFeeTxn.setTxnId(idWorker.nextId());
        refundFeeTxn.setCurrencyCode(account.getCurrencyCode());
        refundFeeTxn.setTxnAmt(iouReceipt.getIouAmt());//原始交易金额
        refundFeeTxn.setCustomerGenFlag(false);
        refundFeeTxn.setFlowType(txnProfile.getFlowType());
        refundFeeTxn.setTxnDate(request.getBusinessDate());
        refundFeeTxn.setTxnTime(LocalTime.now());

        repository.save(refundFeeTxn);
        return refundFeeTxn;
    }
}
