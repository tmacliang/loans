package org.kelex.loans.core.entity;

import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by hechao on 2017/8/10.
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -5952013196461730872L;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", nullable=false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_DATE", nullable=false)
    private LocalDateTime modifiedDate=LocalDateTime.now();

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, length = 64)
    public String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false)
    public LocalDateTime createdDate = LocalDateTime.now();

    @NotNull
    @Version
    @Column(name = "MODIFIED_NO", nullable=false)
    private Integer modifiedNo = 0;

    private volatile transient boolean modified = false;

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedNo() {
        return modifiedNo;
    }

    public void setModifiedNo(Integer modifiedNo) {
        this.modifiedNo = modifiedNo;
    }

    public boolean markModified(){
        return markModified(true);
    }

    public boolean markModified(boolean b){
        return this.modified=b;
    }

    public boolean isModified(){
        return this.modified;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public abstract Object primaryKey();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseEntity{")
                .append("createdBy = '").append(createdBy).append('\'')
                .append(",createdDate = ").append(createdDate)
                .append(",modified = ").append(modified)
                .append(",modifiedBy = '").append(modifiedBy).append('\'')
                .append(",modifiedDate = ").append(modifiedDate)
                .append(",modifiedNo = ").append(modifiedNo)
                .append(",rkModified = ").append(markModified())
                .append("}");
        return sb.toString();
    }
}
