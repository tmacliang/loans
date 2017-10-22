package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/20.
 */
public class PlanProcessCtrlId implements Serializable {
    private String productId;
    private String actTypeId;
    private String planId;

    @Column(name = "PRODUCT_ID", nullable = false, length = 8)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Column(name = "ACT_TYPE_ID", nullable = false, length = 8)
    public String getActTypeId() {
        return actTypeId;
    }

    public void setActTypeId(String actTypeId) {
        this.actTypeId = actTypeId;
    }

    @Column(name = "PLAN_ID", nullable = false, length = 5)
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof PlanProcessCtrlId)) {return false;}
        PlanProcessCtrlId that = (PlanProcessCtrlId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(actTypeId, that.actTypeId) &&
                Objects.equals(planId, that.planId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, actTypeId, planId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlanProcessCtrlId{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",planId = '").append(planId).append('\'')
                .append(",productId = '").append(productId).append('\'')
                .append("}");
        return sb.toString();
    }
}
