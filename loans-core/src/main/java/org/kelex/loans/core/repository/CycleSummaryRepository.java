package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.CycleSummaryId;
import org.kelex.loans.core.entity.CycleSummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/14.
 */
public interface CycleSummaryRepository extends JpaRepository<CycleSummaryEntity, CycleSummaryId> {
}
