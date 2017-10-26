package org.kelex.loans.core.service;

import org.kelex.loans.core.SysintrException;
import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.core.util.TransactionUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.math.BigDecimal.ZERO;

/**
 * Created by hechao on 2017/10/17.
 */
@Service
public class PostingService {

    @Inject
    private EntryService entryService;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private CycleSummaryRepository cycleSummaryRepository;

    @Inject
    private BalCompValRepository balCompValRepository;

    @Inject
    private TxnProfileRepository txnProfileRepository;

    @Inject
    private ActCreditDataRepository actCreditDataRepository;

    private void checkArguments(TransactionContext context) {

    }

    public void postTransactions(TransactionContext context) {
        checkArguments(context);
        RepositoryProxy repository = context.getRepository();
        Map<Object, TxnSummaryEntity> txns = repository.get(TxnSummaryEntity.class);
        for (TxnSummaryEntity txn : txns.values()) {
            postOneTxn(txn, context);
        }
    }

    private void postOneTxn(TxnSummaryEntity txn, TransactionContext context) {
        RepositoryProxy repository = context.getRepository();

        TxnSummaryId txnId = txn.getId();
        AccountEntity acccount = accountRepository.findOne(txnId.getAccountId());

        CycleSummaryId cycleId = acccount.getCycleId();
        CycleSummaryEntity cycle = cycleSummaryRepository.findOne(cycleId);

        List<BalCompValEntity> balCompValList = balCompValRepository.findAll(cycleId.getAccountId(), cycleId.getCycleNo());
        Map<String, BalCompValEntity> bcvMap = TransactionUtils.toMap(balCompValList);

        String productId = txn.getProductId();
        String actTypeId = txn.getActTypeId();
        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(txn.getTxnCode(), repository);

        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, txn.getTxnCode(), repository);
        if (txnCtrl == null) {
            throw new SysintrException("txn ctrl is no such: " + productId + ", " + actTypeId + ", " + ", " + txn.getTxnCode());
        }

        BigDecimal residueAmt = txn.getPostingAmt();

        String initBcpId = txnCtrl.getInitBcpId();
        /*if (initBcpId != null) {
            BalProcessCtrlEntity initBalCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, initBcpId, repository);
            if (initBalCtrl == null) {
                throw new SysintrException("txn init bal ctrl is no such: " + productId + ", " + actTypeId + ", " + initBcpId);
            }
            BalCompProfileEntity initBcp = entryService.findOneBalCompProfile(initBcpId, repository);
            if (initBcp == null) {
                throw new SysintrException("txn init bal profile is no such: " + initBcpId);
            }
        }*/

        BalProcessCtrlEntity balCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, txnCtrl.getBcpId(), repository);
        String bcpId = txnCtrl.getBcpId();
        if (balCtrl == null) {
            throw new SysintrException("txn bal ctrl is no such: " + productId + ", " + actTypeId + ", " + bcpId);
        }
        BalCompProfileEntity bcp = entryService.findOneBalCompProfile(bcpId, repository);
        if (bcp == null) {
            throw new SysintrException("txn bal profile is no such: " + bcpId);
        }

        BalCompValEntity bcv = bcvMap.get(bcpId);
        if (bcv == null) {
            BalCompValId newBcvId = new BalCompValId();
            newBcvId.setAccountId(acccount.getAccountId());
            newBcvId.setCycleNo(acccount.getCurrentCycleNo());
            newBcvId.setBcpId(bcpId);

            BalCompValEntity newBcv = new BalCompValEntity();
            newBcv.setId(newBcvId);
            newBcv.setCurrencyCodeEnum(txn.getCurrencyCode());
            newBcv.setFlowType(bcp.getFlowType());
            newBcv.setBalType(bcp.getBalType());
            newBcv.setCtdBalance(BigDecimal.ZERO);
            newBcv.setPvsBalance(BigDecimal.ZERO);
            newBcv.setOldBalance(BigDecimal.ZERO);
            bcv = newBcv;
            bcvMap.put(bcpId, bcv);
        }

        bcv.setCtdBalance(bcv.getCtdBalance().add(residueAmt));

        repository.save(bcv);

        //TODO:wait
        this.hashCode();


        //更新账户
        updateAccount(txn, context);


    }

    private BigDecimal apportionDebitTxn(TxnSummaryEntity txn, TxnProfileEntity txnProfile, Map<String, BalCompValEntity> bcvMap, RepositoryProxy repository) {

        String productId = txn.getProductId();
        String actTypeId = txn.getActTypeId();

        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, txn.getTxnCode(), repository);
        if (txnCtrl == null) {
            throw new SysintrException("txn ctrl is no such: " + productId + ", " + actTypeId + ", " + ", " + txn.getTxnCode());
        }

        String initBcpId = txnCtrl.getInitBcpId();
        if (initBcpId != null) {
            BalProcessCtrlEntity initBalCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, initBcpId, repository);
            if (initBalCtrl == null) {
                throw new SysintrException("txn init bal ctrl is no such: " + productId + ", " + actTypeId + ", " + initBcpId);
            }
            BalCompProfileEntity initBalProfile = entryService.findOneBalCompProfile(initBalCtrl.getId().getBcpId(), repository);
            if (initBalProfile == null) {
                throw new SysintrException("txn init bal profile is no such: " + initBalCtrl.getId().getBcpId());
            }
            BalCompValEntity initBcv = bcvMap.get(initBcpId);
            if (initBalProfile.isDebitFlow()) {
                if (initBcv == null) {
                    throw new SysintrException("inti bal comp val is no such: " + initBcpId);
                } else {

                }
            } else {
                if (initBcv == null) {

                }
            }
        }

        return BigDecimal.ZERO;
    }

    private void updateAccount(TxnSummaryEntity txnSummary, TransactionContext context) {

        RepositoryProxy repositoryProxy = context.getRepository();
        AccountEntity accountEntity = (AccountEntity) context.getAttribute(AccountEntity.class);
        BigDecimal postingAmt = ZERO;
        BigDecimal osgDeductAmt = ZERO;

        TxnProfileEntity txnProfile = txnProfileRepository.findOne(txnSummary.getTxnCode());
        if (Objects.equals(txnProfile.getFlowType(), "C")) {
            postingAmt = txnSummary.getPostingAmt().negate();
            osgDeductAmt = txnSummary.getOutstandingDeductAmt().negate();
        }
        accountEntity.setCurrentBalance(accountEntity.getCurrentBalance().add(postingAmt));
        accountEntity.setOutstandingTxnAmt(accountEntity.getOutstandingTxnAmt().add(osgDeductAmt));

        switch (txnSummary.getTxnCode()) {
            case "DLQF":
                accountEntity.setTotalDlqFeeAmt(accountEntity.getTotalDlqFeeAmt().add(postingAmt));
                break;
            case "TXNF":
                accountEntity.setTotalTxnFeeAmt(accountEntity.getTotalTxnFeeAmt().add(postingAmt));
                break;
            case "RINT":
                accountEntity.setTotalInterestAmt(accountEntity.getTotalInterestAmt().add(postingAmt));
                break;
        }
        repositoryProxy.save(accountEntity);
        context.setAttribute(TxnProfileEntity.class, txnProfile);
    }

    private void updateCreditLimit(TxnSummaryEntity txnSummary, TransactionContext context) {
        AccountEntity accountEntity = (AccountEntity) context.getAttribute(AccountEntity.class);
        ActCreditDataEntity actCreditData = actCreditDataRepository.findOne(accountEntity.getAccountId());

        TxnProfileEntity txnProfile = (TxnProfileEntity) context.getAttribute(TxnProfileEntity.class);
        if (Objects.equals(txnProfile.getFlowType(), "D")) {
            actCreditData.setTotalPostedAmt(actCreditData.getTotalPostedAmt().add(txnSummary.getPostingAmt()));
            actCreditData.setOutstandingAuthAmt(actCreditData.getOutstandingAuthAmt().add(txnSummary.getOutstandingDeductAmt()));
        } else {
            actCreditData.setTotalPostedAmt(actCreditData.getTotalPostedAmt().subtract(txnSummary.getPostingAmt()));
            actCreditData.setOutstandingAuthAmt(actCreditData.getOutstandingAuthAmt().subtract(txnSummary.getOutstandingDeductAmt()));
        }

        BigDecimal availableBalance = actCreditData.getCreditLimit().subtract(actCreditData.getTotalPostedAmt().subtract(actCreditData.getOutstandingAuthAmt()));
        if (availableBalance.compareTo(ZERO) <= 0) {
            availableBalance = ZERO;
        }
        //TODO:判断临时额度

        actCreditData.setAvailableBalance(availableBalance);
        context.setAttribute(AccountEntity.class, actCreditData);
    }

}
