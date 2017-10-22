package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.PlanProcessCtrlId;
import org.kelex.loans.core.entity.PlanProcessCtrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by hechao on 2017/9/20.
 */
public interface PlanProcessCtrlRepository extends JpaRepository<PlanProcessCtrlEntity, PlanProcessCtrlId> {

    PlanProcessCtrlEntity findOneByIdAndActiveFlag(PlanProcessCtrlId id, Boolean activeFlag);

    @Query(value = "SELECT * FROM plan_process_ctrl WHERE PRODUCT_ID=?1 AND ACT_TYPE_ID=?2 AND PLAN_ID=?3 AND ACTIVE_FLAG='Y'",
            nativeQuery = true)
    PlanProcessCtrlEntity findOne(String productId, String actTypeId, String planId);
}
