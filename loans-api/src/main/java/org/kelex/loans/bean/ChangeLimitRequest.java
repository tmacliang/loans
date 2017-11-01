package org.kelex.loans.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by licl on 2017/10/18.
 */

public interface ChangeLimitRequest {
    Long getAccountId();

    Boolean getPermanent();

    BigDecimal getCreditLimit();

    LocalDate getEffectiveStartDate();

    LocalDate getEffectiveEndDate();

}
