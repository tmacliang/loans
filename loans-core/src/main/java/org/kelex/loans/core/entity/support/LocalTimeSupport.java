package org.kelex.loans.core.entity.support;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalTime;

/**
 * Created by hechao on 2017/9/29.
 */
@Converter(autoApply = true)
public class LocalTimeSupport implements AttributeConverter<LocalTime, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LocalTime attribute) {
        return attribute == null ? null : (attribute.getHour() * 10000
                + attribute.getMinute() * 100
                + attribute.getSecond());
    }

    @Override
    public LocalTime convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        int hour = dbData / 10000;
        int rem = dbData % 10000;
        int minute = rem / 100;
        int second = rem % 100;
        return LocalTime.of(hour, minute, second);
    }
}
