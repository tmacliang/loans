package org.kelex.loans.core.service;

import org.kelex.loans.bean.RefundRequest;
import org.kelex.loans.core.context.TransactionRequestContext;

/**
 * Created by licl1 on 2017/11/2.
 */
public class RefundService extends TransactionService<RefundRequest> {
    @Override
    protected void checkArguments(TransactionRequestContext<? extends RefundRequest> context) throws Exception {

    }

    @Override
    protected void checkBusinessLogic(TransactionRequestContext<? extends RefundRequest> context) throws Exception {

    }

    @Override
    protected void doTransaction(TransactionRequestContext<? extends RefundRequest> context) throws Exception {

    }
}
