package org.kelex.loans.core.service;

import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.entity.BalCompValEntity;
import org.kelex.loans.core.entity.BalProcessCtrlEntity;
import org.kelex.loans.core.entity.TxnSummaryEntity;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.BalCompValRepository;
import org.kelex.loans.core.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
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

        List<TxnSummaryEntity> interestTxnList = accrueInterestTxn(balCompValList, context);
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
    public List<TxnSummaryEntity> accrueInterestTxn(List<BalCompValEntity> balCompValList, TransactionContext context) {
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
            TxnSummaryEntity txnSummary = createOneInterestTxn(context);
            txnList.add(txnSummary);
        }

        return txnList;
    }

    public TxnSummaryEntity createOneInterestTxn(TransactionContext context) {
        TxnSummaryEntity txnSummary = new TxnSummaryEntity();
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
