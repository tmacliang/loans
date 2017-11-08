package org.kelex.loans.core.service;

import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.BalCompValRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.ZERO;

/**
 * Created by licl1 on 2017/11/7.
 */
@Service
public class InterestService {

    @Inject
    private EntryService entryService;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private BalCompValRepository balCompValRepository;

    public List<TxnSummaryEntity> createInterestTxnList(Long accountId, TransactionContext context) {
        AccountEntity account = accountRepository.findOne(accountId);
        context.setAttribute(AccountEntity.class, account);

        List<BalCompValEntity> balCompValList = findBalCompValList(context);
        if (balCompValList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TxnSummaryEntity> interestTxnList = billInterest(balCompValList, context);
        return interestTxnList;
    }

    /**
     * 查询当前账户的余额成分
     *
     * @param context
     * @return
     */
    public List<BalCompValEntity> findBalCompValList(TransactionContext context) {

        if (!freeAccrueInterest(context)) {
            return Collections.emptyList();
        }

        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);

        List<BalCompValEntity> balCompValList = balCompValRepository.findAll(account.getAccountId(), account.getCurrentCycleNo());

        for (BalCompValEntity bcv : balCompValList) {

            long days = ChronoUnit.DAYS.between(bcv.getAccruedThroughDate(), LocalDate.now());//当前日期和计息日期之差
            if (days <= 0) {
                continue;
            }
            BigDecimal rate = account.getInterestRate();

            //查询余额成分控制
            BalProcessCtrlEntity balProcessCtrl = entryService.findOneBalProcessCtrl(account.getProductId(), account.getActTypeId(), bcv.getBalType(), context.getRepository());

            //如果余额成分免息
            if (balProcessCtrl.getWaiveInterestFlag()) {
                continue;
            }

            BigDecimal finalRate = rate.multiply(new BigDecimal(days));
            if (bcv.getOldBalance().compareTo(ZERO) > 0) {
                BigDecimal oldInterests = bcv.getOldBalance().multiply(finalRate);
                bcv.setOldInterestVal(oldInterests);
            }

            if (bcv.getPvsBalance().compareTo(ZERO) > 0) {
                BigDecimal pvsInterests = bcv.getOldBalance().multiply(finalRate);
                bcv.setPvsInterestVal(pvsInterests);
            }

            bcv.setAccruedThroughDate(LocalDate.now());
        }

        return balCompValList;
    }

    /**
     * 创建利息交易
     *
     * @param balCompValList
     * @param context
     * @return
     */
    public List<TxnSummaryEntity> billInterest(List<BalCompValEntity> balCompValList, TransactionContext context) {
        List<TxnSummaryEntity> txnList = new ArrayList<>();
        BigDecimal totalInterests = ZERO;
        for (BalCompValEntity bcv : balCompValList) {
            if (bcv.getOldInterestVal().compareTo(ZERO) > 0) {
                totalInterests = totalInterests.add(bcv.getOldInterestVal());
                bcv.setOldInterestVal(ZERO);
            }

            if (bcv.getPvsInterestVal().compareTo(ZERO) > 0) {
                totalInterests = totalInterests.add(bcv.getPvsInterestVal());
                bcv.setPvsInterestVal(ZERO);
            }
        }
        if (totalInterests.compareTo(ZERO) > 0) {
            TxnSummaryEntity txnSummary = createMergedInterestTxn(context);
            txnSummary.setTxnAmt(totalInterests);
            txnSummary.setPostingAmt(totalInterests);
            txnList.add(txnSummary);
        }

        return txnList;
    }

    /**
     * 创建利息交易
     * @param context
     * @return
     */
    public TxnSummaryEntity createMergedInterestTxn(TransactionContext context) {
        AccountEntity account = (AccountEntity)context.getAttribute(AccountEntity.class);
        CycleSummaryEntity cycleSummary = (CycleSummaryEntity)context.getAttribute(CycleSummaryEntity.class);

        RepositoryProxy repositoryProxy = context.getRepository();
        ActProcessCtrlEntity actProcessCtrl = entryService.findOneActProcessCtrl(account.getProductId(),account.getActTypeId(),repositoryProxy);
        String txnCode = actProcessCtrl.getInterestTxnCode();
        TxnProfileEntity txnProfileEntity = entryService.findOneTxnProfile(txnCode,repositoryProxy);

        TxnSummaryId id = new TxnSummaryId();
        id.setCycleNo(account.getCurrentCycleNo());
        id.setAccountId(account.getAccountId());
        id.setTxnSummaryNo(cycleSummary.getNextTxnSummaryNo());
        TxnSummaryEntity txnSummary = new TxnSummaryEntity();
        txnSummary.setId(id);
        txnSummary.setTxnId(context.getIdWorker().nextId());
        txnSummary.setCustomerId(account.getCustomerId());
        txnSummary.setIouId(0L);
        txnSummary.setOriginalTxnId(null);
        txnSummary.setProductId(account.getProductId());
        txnSummary.setActTypeId(account.getActTypeId());
        txnSummary.setGenTxnSummaryNo(0);
        txnSummary.setTermNo(0);
        txnSummary.setGenFeeAmt(ZERO);
        txnSummary.setTxnCode(txnCode);
        txnSummary.setTxnType(txnProfileEntity.getTxnType());
        txnSummary.setTxnUuid(null);
        txnSummary.setOrderNo(null);
        txnSummary.setCurrencyCode(account.getCurrencyCode());
        txnSummary.setFlowType(txnProfileEntity.getFlowType());
        txnSummary.setCustomerGenFlag(false);
        txnSummary.setTxnDate(LocalDate.now());
        txnSummary.setTxnTime(LocalTime.now());
        txnSummary.setTxnAmt(ZERO);
        txnSummary.setPostingAmt(ZERO);
        txnSummary.setOutstandingDeductAmt(ZERO);

        cycleSummary.setNextTxnSummaryNo(cycleSummary.getNextTxnSummaryNo() + 1);

        repositoryProxy.save(cycleSummary);
        return txnSummary;
    }

    /**
     * 判断用户是否免息
     *
     * @param context
     * @return
     */
    public boolean freeAccrueInterest(TransactionContext context) {
        LocalDate now = LocalDate.now();
        AccountEntity account = (AccountEntity) context.getAttribute(AccountEntity.class);
        if (account.needAccrueInterest()) {
            return false;
        }
        if (account.getCurrentBalance().compareTo(ZERO) <= 0) {
            return true;
        }

        if (account.getInterestRate().compareTo(ZERO) == 0) {
            return true;
        }

        if (account.getWaiveInterestFlag()) {
            if (now.isAfter(account.getWaiveOtherFeeStartDate()) && account.getWaiveInterestEndDate().isAfter(now)) {
                return true;
            }
        }
        return false;
    }


}
