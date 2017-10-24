package org.kelex.loans.core.service;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.RetailRequest;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.CycleSummaryRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.core.util.InstalmentSample;
import org.kelex.loans.core.util.IdWorker;
import org.kelex.loans.core.util.TransactionUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/9/18.
 */
@Service
public class RetailService extends TransactionService<RetailRequest> {

    @Inject
    private EntryService entryService;

    @Inject
    private CycleService cycleService;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private CycleSummaryRepository cycleSummaryRepository;

    @Inject
    private PostingService postingService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends RetailRequest> context) {
        RequestDTO<? extends RetailRequest> request = context.getRequest();
        RetailRequest data = request.getData();
        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getMerchantName(), ArgumentMessageEnum.ERROR_MERCHANTNAME_ISNULL);
        AssertUtils.notNull(data.getCommodityCode(), ArgumentMessageEnum.ERROR_COMMODITYCODE_ISNULL);
        AssertUtils.notNull(data.getRetailOrderNo(), ArgumentMessageEnum.ERROR_RETAILORDERNO_ISNULL);
        AssertUtils.notNull(data.getRetailAmount(), ArgumentMessageEnum.ERROR_RETAILAMOUNT_ISNULL);
        AssertUtils.notNull(data.getTotalTerms(), ArgumentMessageEnum.ERROR_TOTALTERMS_ISNULL);
        AssertUtils.notNull(data.getCurrencyCodeEnum(), ArgumentMessageEnum.ERROR_CURRENCYCODE_ISNULL);
        //RetailAmount > 0 && totalTerms > 0
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RetailRequest> context) {
        RequestDTO<? extends RetailRequest> request = context.getRequest();
        RetailRequest data = request.getData();
        RepositoryProxy repository = context.getRepository();
        Long accountId = data.getAccountId();

        //检查并得到账户实体
        AccountEntity account = accountRepository.findOne(accountId);
        AssertUtils.notNull(account, ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);

        String productId = account.getProductId();
        String actTypeId = account.getActTypeId();

        //检查并得到当前账期
        CycleSummaryEntity cycle = cycleSummaryRepository.findOne(account.getCycleId());

        String planId = TransactionUtils.toRetailPlanId(data.getTotalTerms());
        //检查并得到交易计划配置实体
        PlanProfileEntity planProfile = entryService.findOnePlanProfile(planId, repository);

        //检查并得到交易计划控制实体
        PlanProcessCtrlEntity planCtrl = entryService.findOnePlanProcessCtrl(productId, actTypeId, planId, repository);

        //检查并得到货币控制实体
        CurrProcessCtrlEntity currCtrl = entryService.findOneCurrProcessCtrl(planCtrl.getCurrencyCodeEnum(), planCtrl.getCurrencyUnit(), repository);

        //检查并得到首期控制
        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, planCtrl.getFirstTxnCode(), repository);

        //检查并得到首期交易配置
        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(planCtrl.getFirstTxnCode(), repository);

        //TODO:验证订单是否存在、余额是否充足
        context.setAttribute(AccountEntity.class, account);
        context.setAttribute(CycleSummaryEntity.class, cycle);
        context.setAttribute(PlanProfileEntity.class, planProfile);
        context.setAttribute(PlanProcessCtrlEntity.class, planCtrl);
        context.setAttribute(CurrProcessCtrlEntity.class, currCtrl);
        context.setAttribute(TxnProcessCtrlEntity.class, txnCtrl);
        context.setAttribute(TxnProfileEntity.class, txnProfile);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RetailRequest> context) throws Exception {
        RequestDTO<? extends RetailRequest> request = context.getRequest();
        RepositoryProxy repository = context.getRepository();
        LocalDate businessDate = request.getBusinessDate();
        RetailRequest data = request.getData();

        PlanProcessCtrlEntity planCtrl = (PlanProcessCtrlEntity) context.getAttribute(PlanProcessCtrlEntity.class);
        PlanProfileEntity planProfile = (PlanProfileEntity) context.getAttribute(PlanProfileEntity.class);
        CurrProcessCtrlEntity currCtrl = (CurrProcessCtrlEntity) context.getAttribute(CurrProcessCtrlEntity.class);
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        CycleSummaryEntity cycle = (CycleSummaryEntity) context.getAttribute(CycleSummaryEntity.class);
        TxnProcessCtrlEntity firstCtrl = (TxnProcessCtrlEntity) context.getAttribute(TxnProcessCtrlEntity.class);

        BigDecimal retailAmount = data.getRetailAmount().multiply(BigDecimal.ONE.scaleByPowerOfTen(currCtrl.getPower()));
        InstalmentSample sample = TransactionUtils.getInstalmentSample(retailAmount, account, planProfile, planCtrl, firstCtrl, currCtrl, businessDate);

        IdWorker idWorker = context.getIdWorker();
        IouReceiptEntity iou = new IouReceiptEntity();
        iou.setIouId(idWorker.nextId());
        iou.setPlanId(planCtrl.getId().getPlanId());
        iou.setAccountId(account.getAccountId());
        iou.setCycleNo(account.getCurrentCycleNo());
        iou.setTxnUuid(data.getRetailOrderNo());
        iou.setOrderNo(data.getRetailOrderNo());
        iou.setTxnDate(businessDate);
        iou.setTxnTime(LocalTime.now());
        iou.setCurrentTermNo(0);
        iou.setOutstandingTerms(planProfile.getPlanTerms());
        iou.setIouTerms(planProfile.getPlanTerms());
        iou.setStatusCode(planProfile.getInitStatusCode());
        iou.setCurrencyCodeEnum(account.getCurrencyCodeEnum());

        iou.setPostingFeeAmt(BigDecimal.ZERO);
        iou.setCurrentBalance(BigDecimal.ZERO);
        iou.setIouAmt(retailAmount);
        iou.setIouTerms(planProfile.getPlanTerms());
        iou.setOriginalIouAmt(retailAmount);
        iou.setOriginalIouTerms(planProfile.getPlanTerms());
        iou.setOutstandingTxnAmt(retailAmount);

        iou.setFirstTermAmt(sample.getFirstTermAmt());
        iou.setFixedTermAmt(sample.getFixedTermAmt());
        iou.setLastTermAmt(sample.getLastTermAmt());

        iou.setFeeRate(sample.getFeeRate());
        iou.setFeeRateType(account.getTxnFeeRateType());
        iou.setFirstTermFeeAmt(sample.getFirstTermFeeAmt());
        iou.setFixedTermFeeAmt(sample.getFixedTermFeeAmt());
        iou.setLastTermFeeAmt(sample.getLastTermFeeAmt());

        iou.setTotalPayments(0);
        iou.setTotalPaymentAmt(BigDecimal.ZERO);
        iou.setTotalReversal(0);
        iou.setTotalReversalAmt(BigDecimal.ZERO);
        iou.setCommodityCode(data.getCommodityCode());
        iou.setMerchantName(data.getMerchantName());

        cycleService.next(account, cycle, iou, businessDate, context);

        postingService.postTransactions(context);
    }
}
