package org.kelex.loans.core.repository;

import org.kelex.loans.core.context.ServerApplicationContext;
import org.kelex.loans.core.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;

/**
 * Created by hechao on 2017/9/11.
 */
public class JapRepositoryProxy extends RepositoryProxy {

    private Repositories repositories;

    public JapRepositoryProxy(ServerApplicationContext context) {
        super(context);
        repositories = context.getRepositories();
    }

    @Override
    public void commit(BaseEntity entity) {
        JpaRepository repository = (JpaRepository)repositories.getRepositoryFor(entity.getClass());
        repository.saveAndFlush(entity);
    }
}
