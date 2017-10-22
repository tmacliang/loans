package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.StatusCodeEntity;
import org.kelex.loans.core.entity.StatusCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/27.
 */
public interface StatusCodeRepository extends JpaRepository<StatusCodeEntity, StatusCodeId>{
}
