package org.kelex.loans.core.cache;

import org.springframework.cache.Cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by hechao on 2017/9/8.
 */
public class ExclusiveCache extends ActiveCache {

    private Cache cache;

    private Map<Class, Integer> included;


    public ExclusiveCache(Cache cache, List<Class> includes) {
        this.cache = cache;
        this.included = new HashMap<Class, Integer>();
        for(Class cls : includes){
            this.included.put(cls, Integer.MIN_VALUE);
        }
    }

    public boolean incluable(Class cls){
        return included.containsKey(cls);
    }

    @Override
    public String getName() {
        return cache.getName();
    }

    @Override
    public Object getNativeCache() {
        return cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return cache.get(key, type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return cache.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {
        if(this.included.containsKey(value.getClass())){
            this.cache.put(key, value);
        }else{
            throw new IllegalArgumentException(value.getClass().getName());
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        if(this.included.containsKey(value.getClass())){
            return cache.putIfAbsent(key, value);
        }
        return null;
    }

    @Override
    public void evict(Object key) {
        this.cache.evict(key);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }
}
