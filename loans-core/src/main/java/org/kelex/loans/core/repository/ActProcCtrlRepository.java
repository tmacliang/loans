package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.ActProcessCtrlEntity;
import org.kelex.loans.core.entity.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/7.
 */
public interface ActProcCtrlRepository extends JpaRepository<ActProcessCtrlEntity, ProductId> {

}
