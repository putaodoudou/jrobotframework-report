package org.jspringbot.report;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author yanshuai
 */
public class DateConverter extends AbstractSingleValueConverter {

    public DateConverter(String defaultFormat) {
        df = new SimpleDateFormat(defaultFormat);
    }

    @Override
    public Object fromString(String str) {
        if (null == str || "N/A".equals(str)) {
            return new Date(0L);
        }
        try {
            return df.parse(str);
        } catch (ParseException ex) {
            return new Date(0L);
        }
    }

    @Override
    public String toString(Object obj) {
        final Date date = (Date) obj;
        if (0L == date.getTime()) {
            return "N/A";
        }
        return df.format(date);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Date.class);
    }

    private final DateFormat df;
}
