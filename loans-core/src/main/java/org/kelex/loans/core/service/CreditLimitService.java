package org.kelex.loans.core.service;

import org.kelex.loans.bean.CreditLimitRequest;
import org.kelex.loans.bean.RetailRequest;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.ActCreditDataEntity;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by licl1 on 2017/9/18.
 */
@Service
@Transactional
public class CreditLimitService extends TransactionService<CreditLimitRequest> {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private EntryService entryService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends CreditLimitRequest> context) {

//        CreditLimit request = context.getRequest().getData();

       /* //检查参数不为空
        AssertUtils.notNull(request.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ID_ISNULL);

        AssertUtils.notNull(request.getCreditLimit(), ArgumentMessageEnum.ERROR_ACTIONCODE_ISNULL);

        AssertUtils.notNull(request.getPermanent(), ArgumentMessageEnum.ERROR_ACTIONCODE_ISNULL);

        AssertUtils.notNull(request.getEffectiveStartDate(), ArgumentMessageEnum.ERROR_ACTIONCODE_ISNULL);

        AssertUtils.notNull(request.getEffectiveEndDate(), ArgumentMessageEnum.ERROR_ACTIONCODE_ISNULL);*/


    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends CreditLimitRequest> context) {

    /*    CreditLimit request = context.getRequest().getData();

        ActCreditData actCreditData = entityService.loadAndCacheActCreditDataByAccountId(request.getAccountId());

        RepositoryProxy repositoryProxy = context.getRepository();

        repositoryProxy.markAndPut(ActCreditData.class.getName(), actCreditData);*/

    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends CreditLimitRequest> context) {
      /*  RepositoryProxy repositoryProxy = context.getRepository();

        ActCreditData actCreditData = (ActCreditData) repositoryProxy.get(
                ActCreditData.class.getName(), ActCreditData.class);

        CreditLimit request = context.getRequest().getData();

        Boolean permanent = request.getPermanent();

        BigDecimal creditLimitBefore = calCreditLimit(request.getAccountId(), context);
        BigDecimal availableBalanceBefore = calAvailableBalance(request.getAccountId(), context);

        if (permanent) {
            actCreditData.setCreditLimit(request.getCreditLimit());
        } else {
            actCreditData.setTempCreditLimit(request.getCreditLimit());
            actCreditData.setTempCreditStartDate(request.getEffectiveStartDate());
            actCreditData.setTempCreditEndDate(request.getEffectiveEndDate());
        }

        BigDecimal creditLimitAfter = calCreditLimit(request.getAccountId(), context);
        BigDecimal availableBalanceAfter = calAvailableBalance(request.getAccountId(), context);

        ActCreditHistoryId id = new ActCreditHistoryId()
                .accountId(request.getAccountId())
                .limitChangeDate(LocalDate.now())
                .limitChangeTime(LocalTime.now())
                .ccdId("CC01");
        ActCreditHistory actCreditHistory = new ActCreditHistory()
                .id(id)
                .creditLimitBefore(creditLimitBefore)
                .creditLimitAfter(creditLimitAfter)
                .availableBalanceBefore(availableBalanceBefore)
                .availableBalanceAfter(availableBalanceAfter)
                .startDate(request.getEffectiveStartDate())
                .endDate(request.getEffectiveEndDate());

        repositoryProxy.markAndPut(ActCreditHistory.class.getName(), actCreditHistory);*/

    }

    /**
     * 计算可用额度
     *
     * @param accountId
     * @param context
     * @return
     */
    public BigDecimal calAvailableBalance(Long accountId, TransactionRequestContext<?> context) {
        ActCreditDataEntity acd = (ActCreditDataEntity) context.getAttribute(ActCreditDataEntity.class);
        BigDecimal creditLimit = calCreditLimit(accountId, context);
        BigDecimal availableBalance = creditLimit
                .subtract(acd.getOutstandingAuthAmt())
                .subtract(acd.getTotalPostedAmt());
        return availableBalance.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBalance;
    }

    /***
     * 计算信用额度
     *
     * @param accountId
     * @param context
     * @return
     */
    public BigDecimal calCreditLimit(Long accountId, TransactionRequestContext<?> context) {
        LocalDate now = LocalDate.now();
        ActCreditDataEntity acd = (ActCreditDataEntity) context.getAttribute(ActCreditDataEntity.class);
        BigDecimal creditLimit = acd.getCreditLimit();
        if (acd.getTempCreditLimit().compareTo(BigDecimal.ZERO) > 0) {
            if (acd.getTempCreditEndDate() != null && acd.getTempCreditStartDate() != null) {
                if (acd.getTempCreditEndDate().isAfter(acd.getTempCreditStartDate())) {
                    if (acd.getTempCreditEndDate().isAfter(now) && now.isAfter(acd.getTempCreditStartDate())) {
                        creditLimit = acd.getTempCreditLimit();
                    }
                }
            }
        }
        return creditLimit;
    }

}
