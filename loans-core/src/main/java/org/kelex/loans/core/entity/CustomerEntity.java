package org.kelex.loans.core.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by hechao on 2017/8/10.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 4109692562614296444L;

    @Id
    @Column(name = "CUSTOMER_ID", nullable = false, updatable = false)
    private Long customerId;

    @Column(name = "PRODUCT_ID", nullable = false, updatable = false)
    @Size(min = 3, max = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, updatable = false)
    @Size(min = 3, max = 8)
    private String actTypeId;

    @Column(name = "MEMBER_ID", nullable = false, updatable = false)
    private String memberId;

    @Column(name = "MEMBER_TYPE", length = 5, nullable = false)
    private String memberType;

    @Column(name = "MOBILE_PHONE", nullable = false)
    private String mobilePhone;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public Object primaryKey() {
        return customerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomerEntity{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",customerId = ").append(customerId)
                .append(",memberId = '").append(memberId).append('\'')
                .append(",memberType = '").append(memberType).append('\'')
                .append(",mobilePhone = '").append(mobilePhone).append('\'')
                .append(",productId = '").append(productId).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
