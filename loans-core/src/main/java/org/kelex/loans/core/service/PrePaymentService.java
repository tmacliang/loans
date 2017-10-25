package org.kelex.loans.core.service;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.PaymentOrderRequest;
import org.kelex.loans.bean.PrePaymentRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.entity.CurrProcessCtrlEntity;
import org.kelex.loans.core.entity.PaymentHistoryEntity;
import org.kelex.loans.core.entity.PaymentProcessCtrlEntity;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.core.util.IdWorker;
import org.kelex.loans.enumeration.PaymentStatusCodeEnum;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

/**
 * Created by licl1 on 2017/10/25.
 */
@Service
public class PrePaymentService extends TransactionService<PrePaymentRequest> {

    @Inject
    private PaymentHistoryRepository paymentHistoryRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private EntryService entryService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends PrePaymentRequest> context) throws Exception {
        RequestDTO<? extends PrePaymentRequest> request = context.getRequest();
        PrePaymentRequest data = request.getData();
        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getPaymentOrderNo(), ArgumentMessageEnum.ERROR_PAYMENTORDERNO_ISNULL);
        AssertUtils.notNull(data.getPaymentType(), ArgumentMessageEnum.ERROR_PAYMENTTYPE_ISNULL);
        AssertUtils.notNull(data.getPaymentAmount(), ArgumentMessageEnum.ERROR_PAYMENTAMOUNT_ISNULL);
        AssertUtils.notNull(data.getCurrencyCode(), ArgumentMessageEnum.ERROR_CURRENCYCODE_ISNULL);
        AssertUtils.notLessOrEqualThan(data.getPaymentAmount(), ZERO, ArgumentMessageEnum.ERROR_MIN_VALUE);

    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends PrePaymentRequest> context) throws Exception {
        RequestDTO<? extends PrePaymentRequest> request = context.getRequest();
        RepositoryProxy repository = context.getRepository();
        PrePaymentRequest data = request.getData();
        Optional<PaymentHistoryEntity> paymentHistoryOpt = paymentHistoryRepository.findOneByPaymentOrderNo(data.getPaymentOrderNo());
        if (paymentHistoryOpt.isPresent()) {
            throw new ServerRuntimeException(500, "duplicate exception");
        }
        //TODO: account status code

        AccountEntity account = accountRepository.findOne(data.getAccountId());
        String productId = account.getProductId();
        String actTypeId = account.getActTypeId();
        PaymentProcessCtrlEntity payCtrl = entryService.findOnePaymentProcessCtrl(productId, actTypeId, data.getPaymentType(), repository);
        CurrProcessCtrlEntity currCtrl = entryService.findOneCurrProcessCtrl(payCtrl.getCurrencyCode(), payCtrl.getCurrencyUnit(), repository);

        context.setAttribute(AccountEntity.class, account);
        context.setAttribute(PaymentProcessCtrlEntity.class, payCtrl);
        context.setAttribute(CurrProcessCtrlEntity.class, currCtrl);
        context.setAttribute(RequestDTO.class, request);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends PrePaymentRequest> context) throws Exception {
        createPaymentHistory(context);
    }

    private PaymentHistoryEntity createPaymentHistory(TransactionRequestContext<? extends PrePaymentRequest> context) {
        RequestDTO<? extends PrePaymentRequest> request = context.getRequest();
        RepositoryProxy repository = context.getRepository();
        LocalDate businessDate = request.getBusinessDate();
        PrePaymentRequest data = request.getData();
        CurrProcessCtrlEntity currProcessCtrl = (CurrProcessCtrlEntity) context.getAttribute(CurrProcessCtrlEntity.class);
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        BigDecimal paymentAmount = data.getPaymentAmount().multiply(BigDecimal.ONE.scaleByPowerOfTen(currProcessCtrl.getPower()));
        IdWorker idWorker = context.getIdWorker();

        PaymentHistoryEntity paymentHistory = new PaymentHistoryEntity();
        paymentHistory.setPaymentNo(idWorker.nextId());
        paymentHistory.setPaymentOrderNo(data.getPaymentOrderNo());
        paymentHistory.setStatusCode(PaymentStatusCodeEnum.PEND.toString());
        paymentHistory.setCustomerId(account.getCustomerId());
        paymentHistory.setAccountId(account.getAccountId());
        paymentHistory.setIouId(null);
        paymentHistory.setTxnId(null);
        paymentHistory.setProductId(account.getProductId());
        paymentHistory.setPaymentAmt(paymentAmount);
        paymentHistory.setCurrencyCode(data.getCurrencyCode());
        paymentHistory.setPaymentMethod(data.getPaymentType());
        paymentHistory.setRequestDate(businessDate);
        paymentHistory.setRequestTime(LocalTime.now());
        paymentHistory.setResponseDate(null);
        paymentHistory.setResponseTime(null);
        repository.save(paymentHistory);
        return paymentHistory;
    }

}
