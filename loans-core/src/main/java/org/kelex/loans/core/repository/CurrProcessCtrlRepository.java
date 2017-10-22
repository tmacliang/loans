package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.CurrProcessCtrlEntity;
import org.kelex.loans.core.entity.CurrProcessCtrlId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by hechao on 2017/9/21.
 */
public interface CurrProcessCtrlRepository extends JpaRepository<CurrProcessCtrlEntity, CurrProcessCtrlId> {
    @Query(value = "SELECT * FROM curr_process_ctrl WHERE CURRENCY_CODE=?1 AND UNIT=?2",
            nativeQuery = true)
    CurrProcessCtrlEntity findOne(String currencyCode, String unit);
}
