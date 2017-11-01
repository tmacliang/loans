package org.kelex.loans.core.service;

import com.google.common.base.Objects;
import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.bean.RiskControlRequest;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.entity.AccountEntity;
import org.kelex.loans.core.repository.AccountRepository;
import org.kelex.loans.core.util.AssertUtils;

import javax.inject.Inject;

/**
 * Created by licl1 on 2017/11/1.
 */
public class RiskControlService extends TransactionService<RiskControlRequest> {

    @Inject
    private AccountRepository accountRepository;

    @Override
    protected void checkArguments(TransactionRequestContext<? extends RiskControlRequest> context) throws Exception {

        RequestDTO<? extends RiskControlRequest> request = context.getRequest();
        RiskControlRequest data = request.getData();
        AssertUtils.notNull(data.getAccountId(), ArgumentMessageEnum.ERROR_ACCOUNT_ISNULL);
        AssertUtils.notNull(data.getActionCode(), ArgumentMessageEnum.ERROR_ACTIONCODE_ISNULL);
    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RiskControlRequest> context) throws Exception {
        RequestDTO<? extends RiskControlRequest> request = context.getRequest();
        RiskControlRequest data = request.getData();
        AccountEntity account = accountRepository.findOne(data.getAccountId());
        context.setAttribute(AccountEntity.class, account);
    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RiskControlRequest> context) throws Exception {
        RequestDTO<? extends RiskControlRequest> request = context.getRequest();
        RiskControlRequest data = request.getData();
        AccountEntity accountEntity = (AccountEntity)context.getAttribute(AccountEntity.class);

        String manuStatusCode = accountEntity.getManuStatusCode();
        if(Objects.equal(manuStatusCode, "CLOSE")){
            throw new ServerRuntimeException(500, "account is closed");
        }

        String actionCode = data.getActionCode();
        switch(actionCode){
            case "CLOSE":
                accountEntity.setManuStatusCode("CLOSE");
                break;
            default:
                break;
        }



        /***
         * 、 状态为close则不能进行更改状态;
         状态不等于CLOSE、LOCK时都可以改为LOCK
         状态不等于CLOSE、LOCK、STOP时,可以改为STOP
         状态不等于CLOSE时，可以改为NORM
         默认情况状态不等于CLOSE、LOCK、STOP可以改为其它状态。

         优化级：CLOSE>LOCK>STOP
         关户CLOSE操作不可逆
         冻结LOCK不可以还款也不可以交易
         止付STOP下可以还款不可以交易
         */

    }
}
