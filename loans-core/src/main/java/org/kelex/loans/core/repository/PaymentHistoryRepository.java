package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/10/19.
 */
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
}
