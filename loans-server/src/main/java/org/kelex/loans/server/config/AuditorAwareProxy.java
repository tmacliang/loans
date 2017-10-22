package org.kelex.loans.server.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Created by hechao on 2017/9/15.
 */
@Component
public class AuditorAwareProxy implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return "SYS";
    }
}
