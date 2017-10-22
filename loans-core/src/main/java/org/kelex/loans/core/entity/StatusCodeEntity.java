package org.kelex.loans.core.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hechao on 2017/9/27.
 */
@Entity
@Table(name = "status_code")
public class StatusCodeEntity extends DescriptionEntity {

    @Id
    @NotNull
    @EmbeddedId
    private StatusCodeId id;

    @Column(name = "NEXT_CODE", nullable = false, length = 4)
    private String nextCode;

    @Column(name = "LAST_CODE", nullable = false, length = 4)
    private String lastCode;

    @Type(type = "yes_no")
    @Column(name = "NEXT_FLAG", nullable = false)
    private Boolean nextFlag;

    public StatusCodeId getId() {
        return id;
    }

    public void setId(StatusCodeId id) {
        this.id = id;
    }

    public String getNextCode() {
        return nextCode;
    }

    public void setNextCode(String nextCode) {
        this.nextCode = nextCode;
    }

    public String getLastCode() {
        return lastCode;
    }

    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

    public Boolean getNextFlag() {
        return nextFlag;
    }

    public void setNextFlag(Boolean nextFlag) {
        this.nextFlag = nextFlag;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StatusCodeEntity{")
                .append("id = ").append(id)
                .append(",lastCode = '").append(lastCode).append('\'')
                .append(",nextCode = '").append(nextCode).append('\'')
                .append(",nextFlag = ").append(nextFlag)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
