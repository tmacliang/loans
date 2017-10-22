package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/10/20.
 */
@Embeddable
public class PaymentProcessCtrlId implements Serializable {

    @Column(name = "PRODUCT_ID", nullable = false, length = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, length = 8)
    private String actTypeId;

    @Column(name = "PAYMENT_METHOD", nullable = false, length = 5)
    private String paymentMethod;

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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentProcessCtrlId that = (PaymentProcessCtrlId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(actTypeId, that.actTypeId) &&
                Objects.equals(paymentMethod, that.paymentMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, actTypeId, paymentMethod);
    }
}
