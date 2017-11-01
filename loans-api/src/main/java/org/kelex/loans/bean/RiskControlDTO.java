package org.kelex.loans.bean;

import java.io.Serializable;

/**
 * Created by licl1 on 2017/11/1.
 */
public class RiskControlDTO  implements  RiskControlRequest, Serializable{

    private static final long serialVersionUID = 7516844298638136494L;
    private Long accountId;

    private String actionCode;

    @Override
    public Long getAccountId() {
        return accountId;
    }

    @Override
    public String getActionCode() {
        return actionCode;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RiskControlDTO{");
        sb.append("accountId=").append(accountId);
        sb.append(", actionCode='").append(actionCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
