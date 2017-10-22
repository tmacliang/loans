package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/28.
 */
@Embeddable
public class TxnProcessCtrlId implements Serializable {

    private static final long serialVersionUID = 4685216145111514477L;

    @Column(name = "PRODUCT_ID", nullable = false, length = 8)
    private String productId;

    @Column(name = "ACT_TYPE_ID", nullable = false, length = 8)
    private String actTypeId;

    @Column(name = "TXN_CODE", nullable = false, length = 4)
    private String txnCode;

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

    public String getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(String txnCode) {
        this.txnCode = txnCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TxnProcessCtrlId)) {
            return false;
        }
        TxnProcessCtrlId that = (TxnProcessCtrlId) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(actTypeId, that.actTypeId) &&
                Objects.equals(txnCode, that.txnCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, actTypeId, txnCode);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TxnProcessCtrlId{")
                .append("actTypeId = '").append(actTypeId).append('\'')
                .append(",productId = '").append(productId).append('\'')
                .append(",txnCode = '").append(txnCode).append('\'')
                .append("}");
        return sb.toString();
    }
}
