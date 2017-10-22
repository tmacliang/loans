package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.PlanProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/22.
 */
public interface PlanProfileRepository extends JpaRepository<PlanProfileEntity, String> {
}
