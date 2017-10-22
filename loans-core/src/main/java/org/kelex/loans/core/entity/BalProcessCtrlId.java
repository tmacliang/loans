package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/10/17.
 */
@Embeddable
public class BalProcessCtrlId implements Serializable {

    private static final long serialVersionUID = 7518208033314206237L;
    @Column(name = "PRODUCT_ID", nullable = false, length = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, length = 8)
    private String actTypeId;

    @Column(name = "BCP_ID", nullable = false, length = 4)
    private String bcpId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getActTypeId() {
        return actTypeId;
    }

    public void setActTypeId(String actTypeId) {
        this.actTypeId = actTypeId;
    }

    public String getBcpId() {
        return bcpId;
    }

    public void setBcpId(String bcpId) {
        this.bcpId = bcpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalProcessCtrlId that = (BalProcessCtrlId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(actTypeId, that.actTypeId) &&
                Objects.equals(bcpId, that.bcpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, actTypeId, bcpId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalProcessCtrlId{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",bcpId = '").append(bcpId).append('\'')
                .append(",productId = '").append(productId).append('\'')
                .append("}");
        return sb.toString();
    }
}
