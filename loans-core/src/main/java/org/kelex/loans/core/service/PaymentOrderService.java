package org.kelex.loans.core.service;


import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.PaymentOrderRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.entity.CurrProcessCtrlEntity;
import org.kelex.loans.core.entity.PaymentProcessCtrlEntity;
import org.kelex.loans.core.entity.TxnSummaryEntity;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.CycleSummaryRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.repository.TxnSummaryRepository;
import org.kelex.loans.core.util.AssertUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Override
    protected void checkArguments(TransactionRequestContext<? extends PaymentOrderRequest> context) throws Exception {
        RequestDTO<? extends PaymentOrderRequest> request = context.getRequest();
        PaymentOrderRequest data = request.getData();
        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getPaymentOrderNo(), ArgumentMessageEnum.ERROR_PAYMENTORDERNO_ISNULL);
        AssertUtils.notNull(data.getPaymentType(), ArgumentMessageEnum.ERROR_PAYMENTTYPE_ISNULL);
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
        if(txnSummaryOpt.isPresent()){
            throw new ServerRuntimeException(500,"duplicate Exception");
        }


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
