package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.TxnSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hechao on 2017/9/27.
 */
public interface TxnSummaryRepository extends JpaRepository<TxnSummaryEntity, Long> {
    Optional<TxnSummaryEntity> findOneByOrderNo(String orderNo);
}
