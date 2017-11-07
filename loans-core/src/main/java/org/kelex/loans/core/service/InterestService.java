package org.kelex.loans.core.service;

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForArraysOfLong;
import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.BalCompValRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        List<BalCompValEntity> balCompValList = findBalCompValList(account, context);

        context.setAttribute(AccountEntity.class, account);
        return null;
    }

    /**
     * 查询当前账户的余额成分
     *
     * @param account
     * @param context
     * @return
     */
    public List<BalCompValEntity> findBalCompValList(AccountEntity account, TransactionContext context) {

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
     * @param balCompValList
     * @param context
     * @return
     */
    public List<TxnSummaryEntity> accureInterestTxn(List<BalCompValEntity> balCompValList, TransactionContext context) {
        List<TxnSummaryEntity> txnList = new ArrayList<>();
        BigDecimal totalInterests = ZERO;
        for(BalCompValEntity bcv : balCompValList){
            if(bcv.getOldInterestVal().compareTo(ZERO) > 0){
                totalInterests = totalInterests.add(bcv.getOldInterestVal());
                bcv.setOldInterestVal(ZERO);
            }

            if(bcv.getPvsInterestVal().compareTo(ZERO) > 0){
                totalInterests = totalInterests.add(bcv.getPvsInterestVal());
                bcv.setPvsInterestVal(ZERO);
            }
        }
        if(totalInterests.compareTo(ZERO) > 0){
            TxnSummaryEntity txnSummary = createOneInterestTxn(context);
            txnList.add(txnSummary);
        }

        return txnList;
    }

    public TxnSummaryEntity createOneInterestTxn(TransactionContext context){
        TxnSummaryEntity txnSummary = new TxnSummaryEntity();
        return txnSummary;
    }

    /**
     * 判断用户是否免息
     * @param context
     * @return
     */
    public boolean needAccrueInterest(TransactionContext context) {
        return true;
    }


}
