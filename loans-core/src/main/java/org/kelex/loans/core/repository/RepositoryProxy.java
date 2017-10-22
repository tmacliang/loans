package org.kelex.loans.core.repository;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import org.kelex.loans.core.cache.ActiveCache;
import org.kelex.loans.core.context.ServerApplicationContext;
import org.kelex.loans.core.entity.BaseEntity;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by hechao on 2017/8/29.
 */
public abstract class RepositoryProxy<T extends BaseEntity>{

    private Table<Class<T>, Object, T> bigTable = Tables.newCustomTable(
            Maps.<Class<T>, Map<Object, T>>newHashMap(), Maps::newLinkedHashMap
    );

    private final ActiveCache cache;

    public RepositoryProxy(ServerApplicationContext context) {
        this.cache = context.getCache();
    }

    public ActiveCache getCache() {
        return cache;
    }

    public abstract void commit(T entity);

    public void save(T entity){
        entity.markModified();
        bigTable.put((Class<T>) entity.getClass(), entity.primaryKey(), entity);
    }

    public Map<Object, T> get(Class<T> cls){
        return bigTable.row(cls);
    }

    public T get(Class<T> rowKey, Object colKey){
        return bigTable.get(rowKey, colKey);
    }

    public void commit() {
        Iterator<T> iterator = bigTable.values().iterator();
        while (iterator.hasNext()){
            commit(iterator.next());
        }
    }
}
