package org.kelex.loans.core.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hechao on 2017/9/28.
 */
@Entity
@Table(name = "txn_process_ctrl")
public class TxnProcessCtrlEntity extends DescriptionEntity {
    @Id
    @EmbeddedId
    @NotNull
    private TxnProcessCtrlId id;

    @Column(name = "FEE_CODE", nullable = true, length = 4)
    private String feeCode;

    @Column(name = "INIT_BCP_ID", nullable = false, length = 4)
    private String initBcpId;

    @Column(name = "BCP_ID", nullable = false, length = 4)
    private String bcpId;


    /**
     * 得到手续费码
     * @return
     */
    public String getFeeCode() {
        return feeCode;
    }

    /**
     * 设置手续费码
     * @param feeCode
     */
    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    /**
     * 得到初始化余额成分ID
     * @return
     */
    public String getInitBcpId() {
        return initBcpId;
    }

    /**
     * 设置初始化余额成分ID
     * @param initBcpId
     */
    public void setInitBcpId(String initBcpId) {
        this.initBcpId = initBcpId;
    }

    /**
     * 得到余额成分ID
     * @return
     */
    public String getBcpId() {
        return bcpId;
    }

    /**
     * 设置余额成分ID
     * @param bcpId
     */
    public void setBcpId(String bcpId) {
        this.bcpId = bcpId;
    }

    /**
     * 得到交易处理ID
     * @return
     */
    public TxnProcessCtrlId getId() {
        return id;
    }

    /**
     * 设置交易处理ID
     * @param id
     */
    public void setId(TxnProcessCtrlId id) {
        this.id = id;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TxnProcessCtrlEntity{")
                .append("bcpId = '").append(bcpId).append('\'')
                .append(",feeCode = '").append(feeCode).append('\'')
                .append(",id = ").append(id)
                .append(",initBcpId = '").append(initBcpId).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
