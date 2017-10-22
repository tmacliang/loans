package org.kelex.loans.core.entity;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hechao on 2017/9/27.
 */
public class StatusCodeId implements Serializable {

    @Column(name = "STATUS_CODE", nullable = false, length = 4)
    private String statusCode;

    @Column(name = "STATUS_TYPE", nullable = false, length = 4)
    private String statusType;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusCodeId)) {
            return false;
        }
        StatusCodeId that = (StatusCodeId) o;
        return Objects.equals(statusCode, that.statusCode) &&
                statusType == that.statusType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusCode, statusType);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatusCodeId{")
                .append("statusCode = '").append(statusCode).append('\'')
                .append(",statusType = ").append(statusType)
                .append("}");
        return sb.toString();
    }
}
