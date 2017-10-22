package org.kelex.loans.core.repository;

import org.kelex.loans.core.entity.ProductId;
import org.kelex.loans.core.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hechao on 2017/9/4.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, ProductId> {

}
