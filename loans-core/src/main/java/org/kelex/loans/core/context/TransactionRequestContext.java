package org.kelex.loans.core.context;

import org.kelex.loans.core.dto.RequestDTO;

/**
 * 交易性事务设备上下文。每次交易请求都会创建新的事务设备上下文。生命周期随着请求的线程结束而结束。
 * Created by hechao on 2017/8/31.
 */
public class TransactionRequestContext<T> extends TransactionContext {

    private RequestDTO<T> request;

    public TransactionRequestContext(RequestDTO<T> request) {
        this.request = request;
    }
    /**
     * 得到请求对象
     * @return
     */
    public RequestDTO<T> getRequest() {
        return request;
    }
}
