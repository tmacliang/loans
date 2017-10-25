package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by hechao on 2017/10/19.
 */
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
    Optional<PaymentHistoryEntity> findOneByPaymentOrderNo(String paymentOrderNo);
}
