package org.kelex.loans.core.context;

import org.kelex.loans.ServerConstants;
import org.kelex.loans.core.cache.ActiveCache;
import org.kelex.loans.core.util.IdWorker;
import org.kelex.loans.core.util.SnowflakeIdWorker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.support.Repositories;

import java.util.Locale;

/**
 * Created by hechao on 2017/8/28.
 */
public class ServerApplicationContext {

    private ApplicationContext parent;

    private Repositories repositories;

    private IdWorker idWorker;

    private ActiveCache cache;

    public ServerApplicationContext(ApplicationContext parent) {
        this.idWorker = createIdWorker(parent);
        this.parent = parent;
        this.repositories = new Repositories(parent);
    }

    public IdWorker getIdWorker() {
        return idWorker;
    }

    public ApplicationContext getParent() {
        return parent;
    }

    public Repositories getRepositories() {
        return repositories;
    }

    public Environment getEnvironment() {
        return parent.getEnvironment();
    }

    public ActiveCache getCache() {
        return cache;
    }

    public void setCache(ActiveCache cache) {
        this.cache = cache;
    }

    public String getMessage(MessageSourceResolvable resolvable, Locale locale) {
        return parent.getMessage(resolvable, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return parent.getMessage(code, args, defaultMessage, locale);
    }

    public String getMessage(String code, Object[] args, String defaultMessage) {
        return parent.getMessage(code, args, defaultMessage, Locale.getDefault());
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        return parent.getMessage(code, args, locale);
    }

    public String getMessage(String code, Object[] args) {
        return parent.getMessage(code, args, Locale.getDefault());
    }

    public String getVersion() {
        return parent.getEnvironment().getProperty(ServerConstants.PROFILE_SERVER_VERSION);
    }

    private IdWorker createIdWorker(ApplicationContext context) {
        try {
            String serverId = context.getEnvironment().getProperty(ServerConstants.PROFILE_SERVER_ID);
            if (serverId == null || serverId.length() == 0) {
                throw new IllegalArgumentException("server id is not defined.");
            }
            return new SnowflakeIdWorker(Long.valueOf(serverId), 10);
        } catch (NumberFormatException nfex) {
            throw new IllegalArgumentException(nfex);
        }
    }
}
