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
import org.xml.sax.SAXException;

/**
 *
 * @author yanshuai
 */
public class SuiteTest {

    @org.testng.annotations.Test
    public void testLoad() throws IOException {
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/Suite.xml");
            suite = (org.jspringbot.report.model.Suite) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(suite);
    }

    @org.testng.annotations.Test(dependsOnMethods = {"testLoad"})
    public void testDump() throws IOException, SAXException {
        String xml = xstream.toXML(suite);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Suite.xml");
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

    public SuiteTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Suite.class);
    }

    private org.jspringbot.report.model.Suite suite;
    private final XStream xstream;
}
