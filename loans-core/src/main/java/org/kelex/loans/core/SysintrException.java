package org.kelex.loans.core;

/**
 * Created by hechao on 2017/10/16.
 */
public class SysintrException extends RuntimeException{
    public SysintrException() {
    }

    public SysintrException(String message) {
        super(message);
    }

    public SysintrException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysintrException(Throwable cause) {
        super(cause);
    }

    public SysintrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
