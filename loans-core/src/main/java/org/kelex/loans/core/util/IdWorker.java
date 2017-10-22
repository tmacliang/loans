package org.kelex.loans.core.util;

/**
 * Created by hechao on 2017/8/28.
 */
public abstract class IdWorker {
    public abstract Long nextId();
    public abstract Long workerId();
    public abstract  Long clusterId();
}