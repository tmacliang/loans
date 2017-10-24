package org.kelex.loans.core.service;

import org.kelex.loans.core.SysintrException;
import org.kelex.loans.core.context.TransactionContext;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.BalCompValRepository;
import org.kelex.loans.core.repository.CycleSummaryRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.TransactionUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

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

    private void checkArguments(TransactionContext context){

    }

    public void postTransactions(TransactionContext context) {
        checkArguments(context);
        RepositoryProxy repository = context.getRepository();
        Map<Object, TxnSummaryEntity> txns = repository.get(TxnSummaryEntity.class);
        for(TxnSummaryEntity txn : txns.values()){
            postOneTxn(txn, context);
        }
    }

    private void postOneTxn(TxnSummaryEntity txn, TransactionContext context){
        RepositoryProxy repository = context.getRepository();

        TxnSummaryId txnId = txn.getId();

        AccountEntity acccount = accountRepository.findOne(txnId.getAccountId());

        CycleSummaryId cycleId = acccount.getCycleId();
        CycleSummaryEntity cycle = cycleSummaryRepository.findOne(cycleId);
        Map<String, BalCompValEntity> bcvMap = TransactionUtils.toMap(balCompValRepository.findAll(cycleId.getAccountId(), cycleId.getCycleNo()));

        String productId = txn.getProductId();
        String actTypeId = txn.getActTypeId();
        TxnProfileEntity txnProfile = entryService.findOneTxnProfile(txn.getTxnCode(), repository);

        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, txn.getTxnCode(), repository);
        if(txnCtrl == null){
            throw new SysintrException("txn ctrl is no such: "+productId+", "+actTypeId+", "+", "+txn.getTxnCode());
        }

        BigDecimal residueAmt = txn.getPostingAmt();

        String initBcpId = txnCtrl.getInitBcpId();
        if(initBcpId!=null){
            BalProcessCtrlEntity initBalCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, initBcpId, repository);
            if(initBalCtrl==null){
                throw new SysintrException("txn init bal ctrl is no such: "+productId+", "+actTypeId+", "+initBcpId);
            }
            BalCompProfileEntity initBcp = entryService.findOneBalCompProfile(initBcpId, repository);
            if(initBcp==null){
                throw new SysintrException("txn init bal profile is no such: "+initBcpId);
            }
        }

        BalProcessCtrlEntity balCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, txnCtrl.getBcpId(), repository);
        String bcpId = txnCtrl.getBcpId();
        if(balCtrl==null){
            throw new SysintrException("txn bal ctrl is no such: "+productId+", "+actTypeId+", "+bcpId);
        }
        BalCompProfileEntity bcp = entryService.findOneBalCompProfile(bcpId, repository);
        if(bcp==null){
            throw new SysintrException("txn bal profile is no such: "+bcpId);
        }

        BalCompValEntity bcv = bcvMap.get(bcpId);
        if(bcv == null){
            BalCompValId newBcvId = new BalCompValId();
            newBcvId.setAccountId(acccount.getAccountId());
            newBcvId.setCycleNo(acccount.getCurrentCycleNo());
            newBcvId.setBcpId(bcpId);

            BalCompValEntity newBcv = new BalCompValEntity();
            newBcv.setId(newBcvId);
            newBcv.setCurrencyCode(txn.getCurrencyCode());
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
    }

    private BigDecimal apportionDebitTxn(TxnSummaryEntity txn, TxnProfileEntity txnProfile, Map<String, BalCompValEntity> bcvMap, RepositoryProxy repository){

        String productId = txn.getProductId();
        String actTypeId = txn.getActTypeId();

        TxnProcessCtrlEntity txnCtrl = entryService.findOneTxnProcessCtrl(productId, actTypeId, txn.getTxnCode(), repository);
        if(txnCtrl == null){
            throw new SysintrException("txn ctrl is no such: "+productId+", "+actTypeId+", "+", "+txn.getTxnCode());
        }

        String initBcpId = txnCtrl.getInitBcpId();
        if(initBcpId != null){
            BalProcessCtrlEntity initBalCtrl = entryService.findOneBalProcessCtrl(productId, actTypeId, initBcpId, repository);
            if(initBalCtrl==null){
                throw new SysintrException("txn init bal ctrl is no such: "+productId+", "+actTypeId+", "+initBcpId);
            }
            BalCompProfileEntity initBalProfile = entryService.findOneBalCompProfile(initBalCtrl.getId().getBcpId(), repository);
            if(initBalProfile==null){
                throw new SysintrException("txn init bal profile is no such: "+initBalCtrl.getId().getBcpId());
            }
            BalCompValEntity initBcv = bcvMap.get(initBcpId);
            if(initBalProfile.isDebitFlow()){
                if(initBcv==null){
                    throw new SysintrException("inti bal comp val is no such: "+initBcpId);
                }else{

                }
            }else{
                if(initBcv == null){

                }
            }
        }

        return BigDecimal.ZERO;
    }

}
