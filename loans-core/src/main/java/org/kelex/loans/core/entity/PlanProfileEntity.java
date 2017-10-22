package org.kelex.loans.core.entity;

import javax.persistence.*;

/**
 * Created by hechao on 2017/9/22.
 */
@Entity
@Table(name = "plan_profile")
public class PlanProfileEntity extends DescriptionEntity {

    private static final long serialVersionUID = -924624625409067274L;

    @Id
    @Column(name = "PLAN_ID", nullable = false, length = 5)
    private String planId;

    @Column(name = "PLAN_TERMS", nullable = false)
    private Integer planTerms;

    @Column(name = "INIT_STATUS_CODE", nullable = false, length = 4)
    private String initStatusCode;

    @Column(name = "STATUS_TYPE", nullable = false)
    private String statusType;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getPlanTerms() {
        return planTerms;
    }

    public void setPlanTerms(Integer planTerms) {
        this.planTerms = planTerms;
    }

    public String getInitStatusCode() {
        return initStatusCode;
    }

    public void setInitStatusCode(String initStatusCode) {
        this.initStatusCode = initStatusCode;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Override
    public Object primaryKey() {
        return planId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlanProfileEntity{")
                .append("initStatusCode = '").append(initStatusCode).append('\'')
                .append(",statusType = '").append(statusType).append('\'')
                .append(",planId = '").append(planId).append('\'')
                .append(",planTerms = ").append(planTerms)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
