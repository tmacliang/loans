package org.kelex.loans.core.service;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.PrePaymentRequest;
import org.kelex.loans.bean.RefundRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.entity.IouReceiptEntity;
import org.kelex.loans.core.entity.TxnProfileEntity;
import org.kelex.loans.core.entity.TxnSummaryEntity;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.IouReceiptRepository;
import org.kelex.loans.core.repository.TxnSummaryRepository;
import org.kelex.loans.core.util.AssertUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

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


    @Override
    protected void checkArguments(TransactionRequestContext<? extends RefundRequest> context) throws Exception {
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();
        AssertUtils.notNull(data.getIouId(), ArgumentMessageEnum.ERROR_IOUID_ISNULL);
        AssertUtils.notNull(data.getRefundAmount(), ArgumentMessageEnum.ERROR_REFUNDAMOUNT_ISNULL);
        AssertUtils.notNull(data.getRefundOrderNo(), ArgumentMessageEnum.ERROR_REFUNDORDERNO_ISNULL);
        AssertUtils.notNull(data.getCurrencyCode(), ArgumentMessageEnum.ERROR_CURRENCYCODE_ISNULL);
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RefundRequest> context) throws Exception {
        RequestDTO<? extends RefundRequest> request = context.getRequest();
        RefundRequest data = request.getData();

        //判断订单是否存在
        Optional<TxnSummaryEntity> txnSummaryOpt = txnSummaryRepository.findOneByOrderNo(data.getRefundOrderNo());
        if(!txnSummaryOpt.isPresent()){
            throw new ServerRuntimeException(500, "txnSummary not exist");
        }
        TxnSummaryEntity txnSummary = txnSummaryOpt.get();
        //判断金额是否不大于可退金额
        BigDecimal refundAmount = data.getRefundAmount();
        BigDecimal availableRefundAmount = txnSummary.getTxnAmt().subtract(txnSummary.getReversalAmt());
        if(refundAmount.compareTo(availableRefundAmount) > 0){
            throw new ServerRuntimeException(500, "refundAmount is too large");
        }
        IouReceiptEntity iouReceipt = iouReceiptRepository.findOne(data.getIouId());
        AccountEntity account = accountRepository.findOne(iouReceipt.getAccountId());

        context.setAttribute(AccountEntity.class, account);
        context.setAttribute(IouReceiptEntity.class, iouReceipt);
        context.setAttribute(TxnSummaryEntity.class, txnSummary);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RefundRequest> context) throws Exception {

    }

//    public Tran
}
