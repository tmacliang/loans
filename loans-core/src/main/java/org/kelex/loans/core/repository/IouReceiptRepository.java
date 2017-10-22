package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.IouReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/10/16.
 */
public interface IouReceiptRepository extends JpaRepository<IouReceiptEntity, Long> {
}
