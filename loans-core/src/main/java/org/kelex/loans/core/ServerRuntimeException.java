package org.kelex.loans.core;

/**
 * Created by hechao on 2017/9/1.
 */
public class ServerRuntimeException extends RuntimeException {

    private int errorCode;

    public ServerRuntimeException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServerRuntimeException(int errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ServerRuntimeException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServerRuntimeException(int errorCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
