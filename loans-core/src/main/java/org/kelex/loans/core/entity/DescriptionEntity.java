package org.kelex.loans.core.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by hechao on 2017/9/4.
 */
@MappedSuperclass
public abstract class DescriptionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1010813780220946289L;

    @Column(name = "DESC_TEXT", nullable = false)
    @Max(value = 200)
    private String descText;

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DescriptionEntity{")
                .append("descText = '").append(descText).append('\'')
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
