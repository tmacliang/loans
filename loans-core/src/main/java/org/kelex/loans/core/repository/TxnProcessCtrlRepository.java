package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.TxnProcessCtrlEntity;
import org.kelex.loans.core.entity.TxnProcessCtrlId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/28.
 */
public interface TxnProcessCtrlRepository extends JpaRepository<TxnProcessCtrlEntity, TxnProcessCtrlId> {
}
