package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.BalCompValEntity;
import org.kelex.loans.core.entity.BalCompValId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by hechao on 2017/10/17.
 */

/**
 * @Author:
 * @Date:
 *
 */

/**
 *
 */
public interface BalCompValRepository extends JpaRepository<BalCompValEntity, BalCompValId> {

    @Query(value = "SELECT * FROM bal_comp_val WHERE account_id=?1 AND CYCLE_NO=?2",
    nativeQuery = true)
    List<BalCompValEntity> findAll(Long accountId, Integer cycleNo);
}