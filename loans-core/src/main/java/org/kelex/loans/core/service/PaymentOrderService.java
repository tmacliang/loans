package org.kelex.loans.core.service;


import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.PaymentOrderRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.core.util.IdWorker;
import org.kelex.loans.enumeration.PaymentStatusCodeEnum;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

/**
 * Created by hechao on 2017/10/18.
 */
@Service
public class PaymentOrderService extends TransactionService<PaymentOrderRequest> {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private CycleSummaryRepository cycleSummaryRepository;

    @Inject
    private EntryService entryService;

    @Inject
    private TxnSummaryRepository txnSummaryRepository;

    @Inject
    private PaymentHistoryRepository paymentHistoryRepository;

    @Inject
    private PostingService postingService;

    @Inject
    private PaymentProcessCtrlRepository paymentProcessCtrlRepository;

    @Inject
    private InterestService interestService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {
        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getPaymentOrderNo(), ArgumentMessageEnum.ERROR_PAYMENTORDERNO_ISNULL);
        AssertUtils.notNull(data.getPaymentStatusCode(), ArgumentMessageEnum.ERROR_PAYMENTTYPE_ISNULL);
        AssertUtils.notNull(data.getPaymentAmount(), ArgumentMessageEnum.ERROR_PAYMENTAMOUNT_ISNULL);
        AssertUtils.notNull(data.getCurrencyCode(), ArgumentMessageEnum.ERROR_CURRENCYCODE_ISNULL);
        AssertUtils.notLessOrEqualThan(data.getPaymentAmount(), ZERO, ArgumentMessageEnum.ERROR_MIN_VALUE);
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {

        RepositoryProxy repository = context.getRepository();
        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
        //幂等性验证
        Optional<TxnSummaryEntity> txnSummaryOpt = txnSummaryRepository.findOneByOrderNo(data.getPaymentOrderNo());
        if (txnSummaryOpt.isPresent()) {
            throw new ServerRuntimeException(500, "duplicate Exception");
        }

        Optional<PaymentHistoryEntity> paymentHistoryOpt = paymentHistoryRepository.findOneByPaymentOrderNo(data.getPaymentOrderNo());
        if (!paymentHistoryOpt.isPresent()) {
            throw new ServerRuntimeException(500, "paymentOrderNo not find PaymentHistory");
        }
        PaymentHistoryEntity paymentHistory = paymentHistoryOpt.get();
        if (Objects.equals(paymentHistory.getStatusCode(), data.getPaymentStatusCode())) {
            throw new ServerRuntimeException(500, "error");
        }
        if (!Objects.equals(paymentHistory.getAccountId(), data.getAccountId())) {
            throw new ServerRuntimeException(500, "error");
        }

        //TODO：金额判断

        AccountEntity accountEntity = accountRepository.findOne(data.getAccountId());
        CycleSummaryId summaryId = new CycleSummaryId();
        summaryId.setAccountId(accountEntity.getAccountId());
        summaryId.setCycleNo(accountEntity.getCurrentCycleNo());
        CycleSummaryEntity cycleSummaryEntity = cycleSummaryRepository.findOne(summaryId);

        PaymentProcessCtrlId paymentProcessCtrlId = new PaymentProcessCtrlId();
        paymentProcessCtrlId.setActTypeId(accountEntity.getActTypeId());
        paymentProcessCtrlId.setProductId(accountEntity.getProductId());
        paymentProcessCtrlId.setPaymentMethod(paymentHistory.getPaymentMethod());
        PaymentProcessCtrlEntity payCtrl = paymentProcessCtrlRepository.findOne(paymentProcessCtrlId);
        //检查并得到货币控制实体
        CurrProcessCtrlEntity currCtrl = entryService.findOneCurrProcessCtrl(payCtrl.getCurrencyCode(), payCtrl.getCurrencyUnit(), repository);

        context.setAttribute(AccountEntity.class, accountEntity);
        context.setAttribute(CycleSummaryEntity.class, cycleSummaryEntity);
        context.setAttribute(PaymentHistoryEntity.class, paymentHistory);
        context.setAttribute(CurrProcessCtrlEntity.class, currCtrl);
        context.setAttribute(PaymentProcessCtrlEntity.class, payCtrl);

    }


    @Override
    protected void doTransaction(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {

        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();

        RepositoryProxy repositoryProxy = context.getRepository();
        LocalDate businessDate = request.getBusinessDate();
        PaymentHistoryEntity paymentHistory = (PaymentHistoryEntity) context.getAttribute(PaymentHistoryEntity.class);
        if (Objects.equals(data.getPaymentStatusCode(), PaymentStatusCodeEnum.SUCC.toString())) {
            processSuccessPayment(context);
            paymentHistory.setStatusCode(PaymentStatusCodeEnum.SUCC.toString());
            paymentHistory.setCompleteDate(LocalDate.now());
            paymentHistory.setResponseDate(businessDate);
            paymentHistory.setResponseTime(LocalTime.now());
        } else {
            paymentHistory.setStatusCode(PaymentStatusCodeEnum.FAIL.toString());
            paymentHistory.setCompleteDate(LocalDate.now());
            paymentHistory.setResponseDate(businessDate);
            paymentHistory.setResponseTime(LocalTime.now());
        }
        repositoryProxy.save(paymentHistory);
        this.hashCode();
    }

    public void processSuccessPayment(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {

        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        CurrProcessCtrlEntity currCtrl = (CurrProcessCtrlEntity) context.getAttribute(CurrProcessCtrlEntity.class);

        BigDecimal paymentAmt = data.getPaymentAmount().multiply(BigDecimal.ONE.scaleByPowerOfTen(currCtrl.getPower()));
        RepositoryProxy repository = context.getRepository();

        RepositoryProxy repositoryProxy = context.getRepository();
        //accrue interest
        if (account.needAccrueInterest()) {
            List<TxnSummaryEntity> interestTxnList = interestService.createInterestTxnList(account.getAccountId(), context);
            for(TxnSummaryEntity txnSummary : interestTxnList){
                repositoryProxy.save(txnSummary);
            }
        }

        //创建还款交易
        TxnSummaryEntity txnSummary = createTxnSummary(context);
        txnSummary.setTxnAmt(paymentAmt);
        txnSummary.setPostingAmt(paymentAmt);
        repository.save(txnSummary);

        postingService.postTransactions(context);
    }


    public TxnSummaryEntity createTxnSummary(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {

        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        CycleSummaryEntity cycleSummary = (CycleSummaryEntity) context.getAttribute(CycleSummaryEntity.class);
        cycleSummary.setNextTxnSummaryNo(cycleSummary.getNextTxnSummaryNo() + 1);

        PaymentProcessCtrlEntity payCtrl = (PaymentProcessCtrlEntity) context.getAttribute(PaymentProcessCtrlEntity.class);

        RepositoryProxy repository = context.getRepository();
        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(payCtrl.getTxnCode(), repository);

        IdWorker idWorker = context.getIdWorker();

        TxnSummaryEntity txnSummary = new TxnSummaryEntity();
        TxnSummaryId txnSummaryId = new TxnSummaryId();
        txnSummaryId.setAccountId(account.getAccountId());
        txnSummaryId.setCycleNo(account.getCurrentCycleNo());
        txnSummaryId.setTxnSummaryNo(cycleSummary.getNextTxnSummaryNo());

        txnSummary.setId(txnSummaryId);
        txnSummary.setTxnId(idWorker.nextId());
        txnSummary.setCustomerId(account.getCustomerId());
        txnSummary.setIouId(0L);
        txnSummary.setOriginalTxnId(0L);
        txnSummary.setProductId(account.getProductId());
        txnSummary.setActTypeId(account.getActTypeId());
        txnSummary.setGenTxnSummaryNo(0);
        txnSummary.setTermNo(0);
        txnSummary.setTxnCode(payCtrl.getTxnCode());
        txnSummary.setTxnType(txnProfile.getTxnType());
        txnSummary.setTxnUuid(data.getPaymentOrderNo());
        txnSummary.setOrderNo(data.getPaymentOrderNo());
        txnSummary.setCurrencyCode(account.getCurrencyCode());
        txnSummary.setFlowType(txnProfile.getFlowType());
        txnSummary.setOutstandingDeductAmt(ZERO);
        txnSummary.setGenFeeAmt(ZERO);
        txnSummary.setReversalAmt(ZERO);
        txnSummary.setCustomerGenFlag(true);
        txnSummary.setTxnDate(request.getBusinessDate());
        txnSummary.setTxnTime(LocalTime.now());

        return txnSummary;
    }


}
