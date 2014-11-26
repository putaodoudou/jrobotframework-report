package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.jspringbot.report.DateConverter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author yanshuai
 */
public class StatisticsTest {

    @Test
    public void testLoad() throws IOException {
        InputStream in = null;
        statistics = null;
        try {
            in = getClass().getResourceAsStream("/Statistics.xml");
            statistics = (Statistics) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(statistics);
    }

    @Test(dependsOnMethods = {"testLoad"})
    public void testDump() throws IOException, SAXException {
        String xml = xstream.toXML(statistics);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Statistics.xml");
            content = IOUtils.toString(in);
        } finally {
            if (null != in) {
                IOUtils.closeQuietly(in);
            }
        }
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(content, xml));
        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals(allDifferences.size(), 0);
    }

    public StatisticsTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Statistics.class);
    }

    private Statistics statistics;
    private final XStream xstream;
}
