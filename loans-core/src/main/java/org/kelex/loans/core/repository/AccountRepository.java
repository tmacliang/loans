package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/7.
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
