package org.kelex.loans.core.dto;

/**
 * Created by hechao on 2017/8/31.
 */
public class ResponseDTO<T> {
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ResponseDTO result(T result){
        setResult(result);
        return this;
    }
}
