package org.kelex.loans.core.context;

import org.kelex.loans.core.dto.RequestDTO;
import org.kelex.loans.core.repository.JapRepositoryProxy;
import org.kelex.loans.core.repository.RepositoryProxy;
import org.kelex.loans.core.util.IdWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hechao on 2017/10/17.
 */
public class TransactionContext {

    private RepositoryProxy repository;

    private Map<Object, Object> attributes = new HashMap<>();

    public TransactionContext(){

    }

    public IdWorker getIdWorker(){
        return getParent().getIdWorker();
    }
    /**
     * 得到父级设备上下文
     * @return
     * @see ServerApplicationContext
     */
    public ServerApplicationContext getParent() {
        return ApplicationContextLoader.getCurrentServerApplicationContext();
    }

    /**
     * 得到数据仓库代理
     * @return
     */
    public synchronized RepositoryProxy getRepository(){
        if(repository == null){
            repository = new JapRepositoryProxy(getParent());
        }
        return repository;
    }

    public void setAttribute(Object name, Object attr){
        attributes.put(name, attr);
    }

    public void romveAttribute(Object name){
        attributes.remove(name);
    }

    public Object getAttribute(Object name){
        return attributes.get(name);
    }

    public Set<Object> getAttributeNames(){
        return attributes.keySet();
    }
}
