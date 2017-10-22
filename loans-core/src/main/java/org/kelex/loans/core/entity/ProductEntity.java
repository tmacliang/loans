package org.kelex.loans.core.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by hechao on 2017/9/4.
 */
@Entity
@Table(name = "product")
public class ProductEntity extends DescriptionEntity implements Serializable {

    private static final long serialVersionUID = 1411245613485552147L;

    @Id
    @EmbeddedId
    private ProductId id;

    public ProductId getId() {
        return id;
    }

    public void setId(ProductId id) {
        this.id = id;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductEntity{")
                .append("id = ").append(id)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
