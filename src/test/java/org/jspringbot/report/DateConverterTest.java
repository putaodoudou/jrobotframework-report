package org.jspringbot.report;

import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author yanshuai
 */
public class DateConverterTest {

    @Test(dataProvider = "testConverterDateDataProvider")
    public void testConvertDate(String str, long timestamp) throws Exception {
        Date d = (Date) converter.fromString(str);
        Assert.assertEquals(d.getTime(), timestamp);
    }

    @DataProvider(name = "testConverterDateDataProvider")
    public Object[][] testConverterDateDataProvider() {
        return new Object[][] {
            {"20141119 08:58:33.028", 1416358713028L},
            {"20141119 18:24:23.380", 1416392663380L}
        };
    }

    @Test
    public void convertNull() throws Exception {
        Date d = (Date) converter.fromString("N/A");
        Assert.assertEquals(d.getTime(), 0L);
    }

    @Test(dataProvider = "testToStringDataProvider")
    public void testToString(Date date, String str) throws Exception {
        Assert.assertEquals(converter.toString(date), str);
    }

    @DataProvider(name = "testToStringDataProvider")
    public Object[][] testToStringDataProvider() {
        return new Object[][] {
            {new Date(1416358713028L), "20141119 08:58:33.028"},
            {new Date(1416392663380L), "20141119 18:24:23.380"},
            {new Date(0), "N/A"}
        };
    }

    public DateConverterTest() {
        converter = new DateConverter("yyyyMMdd HH:mm:ss.SSS");
    }

    private final DateConverter converter;
}
