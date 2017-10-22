package org.kelex.loans.core.config;

import java.util.List;

/**
 * Created by hechao on 2017/9/8.
 */
public class CacheConfiguration {

    private Integer expire;

    private List<Class> allows;

    private boolean enabled = false;

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public List<Class> getAllows() {
        return allows;
    }

    public void setAllows(List<Class> allows) {
        this.allows = allows;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
