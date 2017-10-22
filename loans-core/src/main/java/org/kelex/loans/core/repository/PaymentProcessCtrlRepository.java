package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.PaymentProcessCtrlEntity;
import org.kelex.loans.core.entity.PaymentProcessCtrlId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/10/20.
 */
public interface PaymentProcessCtrlRepository extends JpaRepository<PaymentProcessCtrlEntity, PaymentProcessCtrlId> {
}
