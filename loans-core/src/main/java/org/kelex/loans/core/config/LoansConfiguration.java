package org.kelex.loans.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hechao on 2017/9/8.
 */
@ConfigurationProperties(prefix = "loans", ignoreUnknownFields = false)
public class LoansConfiguration {

    private CacheConfiguration cache = new CacheConfiguration();;

    public CacheConfiguration getCache(){
        return cache;
    }
}
