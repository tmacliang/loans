package org.kelex.loans.core.entity.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/9/29.
 */
@Converter(autoApply = true)
public class LocalDateSupport implements AttributeConverter<LocalDate, Integer> {

    @Override
    public Integer convertToDatabaseColumn(LocalDate attribute) {
        return attribute == null ? null : (attribute.getYear() * 10000
                + attribute.getMonthValue() * 100
                + attribute.getDayOfMonth());
    }

    @Override
    public LocalDate convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        int year = dbData / 10000;
        int rem = dbData % 10000;
        int month = rem / 100;
        int dayOfMonth = rem % 100;
        return LocalDate.of(year, month, dayOfMonth);
    }
}
