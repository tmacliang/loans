package org.kelex.loans.core.service;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.ChangeLimitRequest;
import org.kelex.loans.bean.PrePaymentRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.entity.ActCreditDataEntity;
import org.kelex.loans.core.entity.ActCreditHistoryEntity;
import org.kelex.loans.core.entity.ActCreditHistoryId;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.AssertUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.ZERO;

/**
 * Created by licl1 on 2017/11/1.
 */
@Service
public class ChangeLimitService extends TransactionService<ChangeLimitRequest> {

    @Inject
    private EntryService entryService;

    @Inject
    private CreditLimitService creditLimitService;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends ChangeLimitRequest> context) throws Exception {
        RequestDTO<? extends ChangeLimitRequest> request = context.getRequest();
        ChangeLimitRequest data = request.getData();

        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getPermanent(), ArgumentMessageEnum.ERROR_PERMANENT_ISNULL);
        AssertUtils.notNull(data.getCreditLimit(), ArgumentMessageEnum.ERROR_CREDITLIMIT_ISNULL);
        AssertUtils.notNull(data.getEffectiveStartDate(), ArgumentMessageEnum.ERROR_EFFECTIVESTARTDATE_ISNULL);
        AssertUtils.notNull(data.getEffectiveEndDate(), ArgumentMessageEnum.ERROR_EFFECTIVEENDDATE_ISNULL);

        if (data.getCreditLimit().compareTo(ZERO) < 0) {
            throw new ServerRuntimeException(500, "credit limit less than zero");
        }

        if (!data.getPermanent()) {
            if (data.getEffectiveStartDate().isAfter(data.getEffectiveEndDate())) {
                throw new ServerRuntimeException(500, "effective start date is after the end date");
            }
        }
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends ChangeLimitRequest> context) throws Exception {
        RequestDTO<? extends ChangeLimitRequest> request = context.getRequest();
        ChangeLimitRequest data = request.getData();
        RepositoryProxy repository = context.getRepository();
        ActCreditDataEntity actCreditDataEntity = entryService.findOneActCreditData(data.getAccountId(), repository);
        context.setAttribute(ActCreditDataEntity.class, actCreditDataEntity);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends ChangeLimitRequest> context) throws Exception {


        ActCreditDataEntity actCreditData = (ActCreditDataEntity) context.getAttribute(ActCreditDataEntity.class);
        RequestDTO<? extends ChangeLimitRequest> request = context.getRequest();

        RepositoryProxy repository = context.getRepository();
        ChangeLimitRequest data = request.getData();
        //创建额度历史
        ActCreditHistoryEntity actCreditHistory = createActCreditHistory(context);
        if (data.getPermanent()) {
            actCreditData.setCreditLimit(data.getCreditLimit());
        } else {
            actCreditData.setTempCreditLimit(data.getCreditLimit());
            actCreditData.setTempCreditStartDate(data.getEffectiveStartDate());
            actCreditData.setTempCreditEndDate(data.getEffectiveEndDate());
        }
        context.setAttribute(ActCreditDataEntity.class, actCreditData);
        actCreditHistory.setAvailableBalanceAfter(calAvailableBalance(context));

        repository.save(actCreditData);
        repository.save(actCreditHistory);

    }


    /**
     * 创建额度历史
     *
     * @param context
     * @return
     * @throws Exception
     */
    public ActCreditHistoryEntity createActCreditHistory(TransactionRequestContext<? extends ChangeLimitRequest> context) throws Exception {
        RequestDTO<? extends ChangeLimitRequest> request = context.getRequest();
        ChangeLimitRequest data = request.getData();

        ActCreditDataEntity actCreditData = (ActCreditDataEntity) context.getAttribute(ActCreditDataEntity.class);

        ActCreditHistoryId id = new ActCreditHistoryId();
        id.setAccountId(data.getAccountId());
        id.setCcdId("CC01");
        ActCreditHistoryEntity actCreditHistory = new ActCreditHistoryEntity();
        actCreditHistory.setCreditLimitBefore(actCreditData.getCreditLimit());
        actCreditHistory.setAvailableBalanceBefore(calAvailableBalance(context));
        actCreditHistory.setCreditLimitAfter(data.getCreditLimit());
        if (!data.getPermanent()) {
            actCreditHistory.setStartDate(data.getEffectiveStartDate());
            actCreditHistory.setEndDate(data.getEffectiveEndDate());
        }
        return actCreditHistory;

    }


    /**
     * 计算可用额度
     *
     * @param context
     * @return
     */
    public BigDecimal calAvailableBalance(TransactionRequestContext<?> context) {
        ActCreditDataEntity acd = (ActCreditDataEntity) context.getAttribute(ActCreditDataEntity.class);
        BigDecimal creditLimit = calCreditLimit(context);
        BigDecimal availableBalance = creditLimit
                .subtract(acd.getOutstandingAuthAmt())
                .subtract(acd.getTotalPostedAmt());
        return availableBalance.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBalance;
    }

    /***
     * 计算信用额度
     *
     * @param context
     * @return
     */
    public BigDecimal calCreditLimit(TransactionRequestContext<?> context) {
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
