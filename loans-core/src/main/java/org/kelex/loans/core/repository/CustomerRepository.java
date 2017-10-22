package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by hechao on 2017/8/10.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "SELECT * FROM customer WHERE MEMBER_ID=?1 AND PRODUCT_ID=?2 AND ACT_TYPE_ID=?3",
            nativeQuery = true)
    CustomerEntity findOne(String memberId, String productId, String actTypeId);
}
