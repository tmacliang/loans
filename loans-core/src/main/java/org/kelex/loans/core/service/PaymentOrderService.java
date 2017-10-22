package org.kelex.loans.core.service;


import org.kelex.loans.bean.PaymentOrderRequest;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.CycleSummaryRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @Override
    protected void checkArguments(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {
        //TODO:wait
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {
        //TODO:wait
        RepositoryProxy repository = context.getRepository();
        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
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
    protected void doTransaction(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        CurrProcessCtrlEntity currCtrl = (CurrProcessCtrlEntity) context.getAttribute(CurrProcessCtrlEntity.class);
        PaymentProcessCtrlEntity payCtrl = (PaymentProcessCtrlEntity) context.getAttribute(PaymentProcessCtrlEntity.class);

        RequestDTO<? extends PaymentOrderRequest> request = (RequestDTO<? extends PaymentOrderRequest>) context.getAttribute(RequestDTO.class);
        LocalDate businessDate = request.getBusinessDate();

        PaymentOrderRequest data = request.getData();
        BigDecimal amount = data.getPaymentAmount().multiply(BigDecimal.ONE.scaleByPowerOfTen(currCtrl.getPower()));

//        PaymentHistoryEntity paymentHistory = new PaymentHistoryEntity();
//        paymentHistory.setProductId(account.getProductId());
//        paymentHistory.setAccountId(account.getAccountId());
//        paymentHistory.setCustomerId(account.getCustomerId());
//        paymentHistory.setCurrencyCode(data.getCurrencyCode());
//        paymentHistory.setPaymentAmt(amount);
//        paymentHistory.setIouId(0L);
//        paymentHistory.setPaymentAmt(data.getPaymentAmount());
//        paymentHistory.setRequestDate(businessDate);
//        paymentHistory.setRequestTime(LocalTime.now());
//        RepositoryProxy repository = context.getRepository();
        this.hashCode();
    }
}
