package org.kelex.loans.core.service;

import org.kelex.loans.core.context.TransactionRequestContext;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hechao on 2017/9/1.
 */
public abstract class TransactionService<T> {

    protected abstract void checkArguments(TransactionRequestContext<? extends T> context) throws Exception;

    protected abstract void checkBusinessLogic(TransactionRequestContext<? extends T> context) throws Exception;

    protected abstract void doTransaction(TransactionRequestContext<? extends T> context) throws Exception;

    protected void commit(TransactionRequestContext<? extends T> context) {
        RepositoryProxy repository = context.getRepository();
        repository.commit();
    }

    @Transactional(rollbackFor = Exception.class)
    public void process(TransactionRequestContext<? extends T> context) throws Exception {
        checkArguments(context);
        checkBusinessLogic(context);
        doTransaction(context);
        commit(context);
    }
}
