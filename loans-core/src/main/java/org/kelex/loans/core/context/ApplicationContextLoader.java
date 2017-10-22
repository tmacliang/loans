package org.kelex.loans.core.context;

import com.google.common.cache.CacheBuilder;
import org.kelex.loans.core.cache.ActiveCache;
import org.kelex.loans.core.cache.ExclusiveCache;
import org.kelex.loans.core.cache.SimpleCache;
import org.kelex.loans.core.config.CacheConfiguration;
import org.kelex.loans.core.config.LoansConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by hechao on 2017/8/31.
 */
public abstract class ApplicationContextLoader {

    @Inject
    private ApplicationContext applicationContext;

    private static Map<ClassLoader, ServerApplicationContext> currentContextPerThread =
            new ConcurrentHashMap<>(1);

    private static volatile ServerApplicationContext currentContext;

    private ServerApplicationContext context;

    @PostConstruct
    private void construct(){

        //TODO:移到 ServerApplicationContext 的 initializeContext 方法中
        Object config = applicationContext.getBean(LoansConfiguration.class);
        if(config==null){
            throw new IllegalArgumentException();
        }

        contextInitialized((LoansConfiguration)config);
    }

    private void contextInitialized(LoansConfiguration config){
        if(this.context == null){
            this.context = new ServerApplicationContext(applicationContext);
        }

        ActiveCache cache = createCache(config.getCache());
        this.context.setCache(cache);

        currentContext = this.context;

        currentContextPerThread.put(Thread.currentThread().getContextClassLoader(), this.context);
    }

    private static ActiveCache createCache(CacheConfiguration config){
        if(!config.isEnabled()){
            return new SimpleCache();
        }
        ExclusiveCache cache = new ExclusiveCache(new GuavaCache("", CacheBuilder.newBuilder()
                .expireAfterAccess(config.getExpire(), TimeUnit.SECONDS)
                .build()), config.getAllows());
        cache.setEnabled(config.isEnabled());
        return cache;
    }

    public static ServerApplicationContext getCurrentServerApplicationContext() {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        if (ccl != null) {
            ServerApplicationContext ccpt = currentContextPerThread.get(ccl);
            if (ccpt != null) {
                return ccpt;
            }
        }
        return currentContext;
    }
}
