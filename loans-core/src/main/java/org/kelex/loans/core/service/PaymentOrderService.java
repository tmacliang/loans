package org.kelex.loans.core.service;


import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.PaymentOrderRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.enumeration.PaymentStatusCodeEnum;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
        if (Objects.equals(paymentHistory.getAccountId(), data.getAccountId())) {
            throw new ServerRuntimeException(500, "error");
        }
   /*     if (paymentHistory.getPaymentAmt().compareTo(data.getPaymentAmount()) != 0) {
            throw new ServerRuntimeException(500, "error");
        }*/

        AccountEntity accountEntity = accountRepository.findOne(data.getAccountId());
        CycleSummaryId summaryId = new CycleSummaryId();
        summaryId.setAccountId(accountEntity.getAccountId());
        summaryId.setCycleNo(accountEntity.getCurrentCycleNo());
        CycleSummaryEntity cycleSummaryEntity = cycleSummaryRepository.findOne(summaryId);

        context.setAttribute(AccountEntity.class, accountEntity);
        context.setAttribute(CycleSummaryEntity.class, cycleSummaryEntity);
        context.setAttribute(PaymentHistoryEntity.class, paymentHistory);

    }


    @Override
    protected void doTransaction(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {

        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();

        RepositoryProxy repositoryProxy = context.getRepository();
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        CurrProcessCtrlEntity currCtrl = (CurrProcessCtrlEntity) context.getAttribute(CurrProcessCtrlEntity.class);
        PaymentProcessCtrlEntity payCtrl = (PaymentProcessCtrlEntity) context.getAttribute(PaymentProcessCtrlEntity.class);

        LocalDate businessDate = request.getBusinessDate();
        BigDecimal amount = data.getPaymentAmount().multiply(BigDecimal.ONE.scaleByPowerOfTen(currCtrl.getPower()));

        PaymentHistoryEntity paymentHistory = (PaymentHistoryEntity) context.getAttribute(PaymentHistoryEntity.class);
        if (Objects.equals(data.getPaymentStatusCode(), PaymentStatusCodeEnum.SUCC.toString())) {
            paymentHistory.setStatusCode(PaymentStatusCodeEnum.SUCC.toString());
            paymentHistory.setCompleteDate(LocalDate.now());
            paymentHistory.setResponseDate(businessDate);
            paymentHistory.setResponseTime(LocalTime.now());
        } else if (Objects.equals(data.getPaymentStatusCode(), PaymentStatusCodeEnum.FAIL.toString())) {
            paymentHistory.setStatusCode(PaymentStatusCodeEnum.FAIL.toString());
            paymentHistory.setCompleteDate(LocalDate.now());
            paymentHistory.setResponseDate(businessDate);
            paymentHistory.setResponseTime(LocalTime.now());
        }
        repositoryProxy.save(paymentHistory);
        this.hashCode();
    }
}
