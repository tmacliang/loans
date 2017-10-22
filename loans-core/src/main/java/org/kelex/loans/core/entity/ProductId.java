package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/4.
 */
@Embeddable
public class ProductId implements Serializable {

    private static final long serialVersionUID = -4025953691139670263L;

    @Column(name = "PRODUCT_ID", nullable = false, updatable = false)
    @Size(min = 3, max = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, updatable = false)
    @Size(min = 3, max = 8)
    private String actTypeId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productCode) {
        this.productId = productCode;
    }

    public String getActTypeId() {
        return actTypeId;
    }

    public void setActTypeId(String moduleCode) {
        this.actTypeId = moduleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductId)) {
            return false;
        }
        ProductId productId1 = (ProductId) o;
        return Objects.equals(productId, productId1.productId) &&
                Objects.equals(actTypeId, productId1.actTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, actTypeId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductId{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",productId = '").append(productId).append('\'')
                .append("}");
        return sb.toString();
    }
}
