package org.kelex.loans.core.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by hechao on 2017/9/21.
 */
@Entity
@Table(name = "curr_process_ctrl")
public class CurrProcessCtrlEntity extends DescriptionEntity {

    private static final long serialVersionUID = 3034984384711066779L;

    @Id
    @EmbeddedId
    @NotNull
    private CurrProcessCtrlId id;

    public CurrProcessCtrlId getId() {
        return id;
    }

    public void setId(CurrProcessCtrlId id) {
        this.id = id;
    }

    @Column(name = "POWER", nullable = false, updatable = false)
    private int power;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public Object primaryKey() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CurrProcessCtrlEntity{")
                .append("id = ").append(id)
                .append(",power = ").append(power)
                .append("}, ").append(super.toString()).append('}');
        return sb.toString();
    }
}
