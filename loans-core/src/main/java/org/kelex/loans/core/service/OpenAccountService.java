package org.kelex.loans.core.service;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.OpenAccountRequest;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.entity.support.StatusCodeEnum;
import org.kelex.loans.core.enumeration.FeeRateType;
import org.kelex.loans.core.repository.CustomerRepository;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.AssertUtils;
import org.kelex.loans.core.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by hechao on 2017/8/31.
 */
@Service
public class OpenAccountService extends TransactionService<OpenAccountRequest> {

    public static final int CYCLE_DAY = 28;

    @Inject
    private EntryService entryService;

    @Inject
    private CustomerRepository customerRepository;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends OpenAccountRequest> context) throws Exception {

        RequestDTO<? extends OpenAccountRequest> request = context.getRequest();

        OpenAccountRequest data = request.getData();

        AssertUtils.notNull(data.getCurrencyCode(), ArgumentMessageEnum.ERROR_CURRENCY_CODE_ILLEGAL);

        AssertUtils.notNull(data.getProductCode(), ArgumentMessageEnum.ERROR_PRODUCT_CODE_ISNULL);
        AssertUtils.size(data.getProductCode().length(),
                (Integer) ArgumentMessageEnum.ERROR_PRODUCT_CODE_OUT_OF_RANGE.MIN,
                (Integer) ArgumentMessageEnum.ERROR_PRODUCT_CODE_OUT_OF_RANGE.MAX,
                ArgumentMessageEnum.ERROR_PRODUCT_CODE_OUT_OF_RANGE);

        AssertUtils.notNull(data.getModuleCode(), ArgumentMessageEnum.ERROR_MODULE_CODE_ISNULL);
        AssertUtils.size(data.getProductCode().length(),
                (Integer) ArgumentMessageEnum.ERROR_MODULE_CODE__OUT_OF_RANGE.MIN,
                (Integer) ArgumentMessageEnum.ERROR_MODULE_CODE__OUT_OF_RANGE.MAX,
                ArgumentMessageEnum.ERROR_MODULE_CODE__OUT_OF_RANGE);

        AssertUtils.notNull(data.getMemberId(), ArgumentMessageEnum.ERROR_MEMBER_ID_ISNULL);
        AssertUtils.size(data.getMemberId().length(),
                (Integer) ArgumentMessageEnum.ERROR_MEMBER_ID_OUT_OF_RANGE.MIN,
                (Integer) ArgumentMessageEnum.ERROR_MEMBER_ID_OUT_OF_RANGE.MAX,
                ArgumentMessageEnum.ERROR_MEMBER_ID_OUT_OF_RANGE);

        AssertUtils.notNull(data.getMemberType(), ArgumentMessageEnum.ERROR_MEMBER_TYPE_ISNULL);
        AssertUtils.size(data.getMemberType().length(),
                (Integer) ArgumentMessageEnum.ERROR_MEMBER_TYPE_OUT_OF_RANGE.MIN,
                (Integer) ArgumentMessageEnum.ERROR_MEMBER_TYPE_OUT_OF_RANGE.MAX,
                ArgumentMessageEnum.ERROR_MEMBER_TYPE_OUT_OF_RANGE);

        AssertUtils.notNull(data.getMobilePhone(), ArgumentMessageEnum.ERROR_MOBILE_PHONE_ISNULL);
        AssertUtils.size(data.getMobilePhone().length(),
                (Integer) ArgumentMessageEnum.ERROR_MOBILE_PHONE_OUT_OF_RANGE.MIN,
                (Integer) ArgumentMessageEnum.ERROR_MOBILE_PHONE_OUT_OF_RANGE.MAX,
                ArgumentMessageEnum.ERROR_MOBILE_PHONE_OUT_OF_RANGE);
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends OpenAccountRequest> context) throws Exception {
        RequestDTO<? extends OpenAccountRequest> request = context.getRequest();
        OpenAccountRequest data = request.getData();
        RepositoryProxy repository = context.getRepository();

        String productCode = data.getProductCode();
        String moduleCode = data.getModuleCode();

        //产品验证
        ProductEntity product = entryService.findOneProduct(productCode, moduleCode, repository);
        AssertUtils.notNull(product, ArgumentMessageEnum.ERROR_PRODUCT_NOT_FOUND);

        //账户控制验证
        ActProcessCtrlEntity actProcCtrl = entryService.findOneActProcessCtrl(productCode, moduleCode, repository);
        AssertUtils.notNull(actProcCtrl, ArgumentMessageEnum.ERROR_ACT_PROC_CTRL_ISNULL);

        //幂等性检查
        CustomerEntity customer = customerRepository.findOne(data.getMemberId(), product.getId().getProductId(), product.getId().getActTypeId());
        AssertUtils.isNull(customer, ArgumentMessageEnum.ERROR_CUSTOMER_DUPLICATE);

        context.setAttribute(ProductEntity.class, product);
        context.setAttribute(ActProcessCtrlEntity.class, actProcCtrl);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends OpenAccountRequest> context) throws Exception {
        RequestDTO<? extends OpenAccountRequest> request = context.getRequest();
        IdWorker idWorker = context.getIdWorker();
        OpenAccountRequest data = request.getData();
        LocalDate businessDate = request.getBusinessDate();

        RepositoryProxy repository = context.getRepository();

        ProductEntity product = (ProductEntity) context.getAttribute(ProductEntity.class);
        CustomerEntity customer = createCustomer(product, data, idWorker.nextId());

        ActProcessCtrlEntity actProcCtrl = (ActProcessCtrlEntity) context.getAttribute(ActProcessCtrlEntity.class);
        AccountEntity account = createAccount(product, customer, actProcCtrl, idWorker.nextId(), businessDate);
        account.setCurrencyCode(data.getCurrencyCode());
        account.setCycleTriggerSeq(idWorker.nextId());

        ActCreditDataEntity actCreditDate = createActCreditDate(account, businessDate);

        CycleSummaryEntity cycleSummary = createCycleSummary(account, businessDate);

        repository.save(customer);
        repository.save(account);
        repository.save(actCreditDate);
        repository.save(cycleSummary);

        this.hashCode();
    }

    public static CustomerEntity createCustomer(ProductEntity product, OpenAccountRequest account, Long customerId) {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerId(customerId);
        entity.setMemberId(account.getMemberId());
        entity.setMobilePhone(account.getMobilePhone());
        entity.setMemberType(account.getMemberType());
        entity.setProductId(product.getId().getProductId());
        entity.setActTypeId(product.getId().getActTypeId());
        return entity;
    }

    public static AccountEntity createAccount(ProductEntity product, CustomerEntity customer, ActProcessCtrlEntity actProcCtrl, Long accountId, LocalDate businessDate) {

        ProductId productId = product.getId();
        if (!Objects.equals(productId.getProductId(), customer.getProductId())
                || !Objects.equals(productId.getActTypeId(), customer.getActTypeId())) {
            throw new IllegalArgumentException();
        }

        LocalDate currCycleDate = businessDate.plusMonths(1);
        if (currCycleDate.getDayOfMonth() > CYCLE_DAY) {
            currCycleDate = currCycleDate.withDayOfMonth(CYCLE_DAY);
        }

        AccountEntity account = new AccountEntity();
        account.setCustomerId(customer.getCustomerId());
        account.setAccountId(accountId);
        account.setLevelNo(1);
        account.setLevel1ActId(accountId);
        account.setCurrentCycleNo(1);

        account.setAutoStatusCode(StatusCodeEnum.ACTP.name());
        account.setAutoStatusSetDate(businessDate);
        account.setManuStatusCode(StatusCodeEnum.NORM.name());
        account.setManuStatusSetDate(businessDate);

        account.setProductId(productId.getProductId());
        account.setActTypeId(productId.getActTypeId());

        account.setCurrentBalance(BigDecimal.ZERO);
        account.setOutstandingTxnAmt(BigDecimal.ZERO);
        account.setCreditLimit(new BigDecimal(10000));

        account.setPreferredCycleDay(currCycleDate.getDayOfMonth());
        account.setCurrDueDate(businessDate.plusDays(actProcCtrl.getDefaultInterestFreeDays()));
        account.setCurrCycleDate(currCycleDate);
        account.setPrevCycleDate(businessDate);

        account.setInterestRate(actProcCtrl.getDefaultInterestRate());
        account.setDlqFeeRate(actProcCtrl.getDefaultDlqFeeRate());
        account.setTxnFeeRate(actProcCtrl.getDefaultTxnFeeRate());

        account.setTxnFeeRateType(FeeRateType.NORM);
        account.setWaiveInterestFlag(actProcCtrl.getDefaultInterestRate().compareTo(BigDecimal.ZERO) == 0);
        account.setWaiveInterestStartDate(businessDate);
        account.setWaiveInterestEndDate(businessDate);
        account.setWaiveTxnFeeFlag(actProcCtrl.getDefaultTxnFeeRate().compareTo(BigDecimal.ZERO) == 0);
        account.setWaiveTxnFeeStartDate(businessDate);
        account.setWaiveTxnFeeEndDate(businessDate);
        account.setWaiveOtherFeeFlag(true);
        account.setWaiveOtherFeeStartDate(businessDate);
        account.setWaiveOtherFeeEndDate(businessDate);

        account.setInterestFreeDays(actProcCtrl.getDefaultInterestFreeDays());
        account.setInDlqFlag(false);
        return account;
    }

    public ActCreditDataEntity createActCreditDate(AccountEntity account, LocalDate businessDate) {
        ActCreditDataEntity entity = new ActCreditDataEntity();
        entity.setAccountId(account.getAccountId());
        entity.setWholeFlag(true);
        entity.setCurrencyCode(account.getCurrencyCode());
        entity.setCreditLimit(account.getCreditLimit());
        entity.setAvailableBalance(BigDecimal.ZERO);
        entity.setOutstandingAuthAmt(BigDecimal.ZERO);
        entity.setTotalPostedAmt(BigDecimal.ZERO);
        entity.setLastLimitChangeDate(businessDate);
        entity.setTempCreditLimit(BigDecimal.ZERO);
        entity.setTempCreditStartDate(businessDate);
        entity.setTempCreditEndDate(businessDate);
        return entity;
    }

    public CycleSummaryEntity createCycleSummary(AccountEntity account, LocalDate businessDate) {
        if (businessDate.isAfter(account.getCurrCycleDate())) {
            throw new IllegalArgumentException("currCycleDate < businessDate");
        }
        CycleSummaryEntity entity = new CycleSummaryEntity();
        CycleSummaryId embeddedId = new CycleSummaryId();
        embeddedId.setAccountId(account.getAccountId());
        embeddedId.setCycleNo(account.getCurrentCycleNo());
        entity.setId(embeddedId);
        entity.setCurrencyCode(account.getCurrencyCode());
        entity.setNextTxnSummaryNo(1);
        entity.setCloseBalance(BigDecimal.ZERO);
        entity.setOpenBalance(BigDecimal.ZERO);
        entity.setOpenTotalDue(BigDecimal.ZERO);
        entity.setTotalPaymentAmt(BigDecimal.ZERO);
        entity.setTotalCycleAmt(BigDecimal.ZERO);
        entity.setTotalCreditAmt(BigDecimal.ZERO);
        entity.setTotalDebitAmt(BigDecimal.ZERO);
        entity.setTotalGraceAmt(BigDecimal.ZERO);
        entity.setTotalGraceCreditAmt(BigDecimal.ZERO);
        entity.setTotalGraceDebitAmt(BigDecimal.ZERO);
        entity.setTotalTxns(0);
        entity.setTotalCredits(0);
        entity.setTotalDebits(0);
        entity.setStartDate(businessDate);
        entity.setEndDate(account.getCurrCycleDate());
        entity.setOpenDueDate(account.getCurrDueDate());
        return entity;
    }
}
