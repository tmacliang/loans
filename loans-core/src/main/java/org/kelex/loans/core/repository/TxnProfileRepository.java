package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.TxnProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/28.
 */
public interface TxnProfileRepository extends JpaRepository<TxnProfileEntity, String> {
}
