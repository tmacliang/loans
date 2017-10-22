package org.kelex.loans.core.cache;

import org.springframework.cache.Cache;

/**
 * Created by hechao on 2017/9/25.
 */
public abstract class ActiveCache implements Cache{

    private boolean enabled;

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }
}
