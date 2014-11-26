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
public class TestTest {

    @Test
    public void testLoad() throws IOException {
        InputStream in = null;
        test = null;
        try {
            in = getClass().getResourceAsStream("/Test.xml");
            test = (org.jspringbot.report.model.Test) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(test);
        Assert.assertEquals(test.getDoc(), "");
        Assert.assertEquals(test.getId(), "s1-s1-s1-t1");
        Assert.assertEquals(test.getName(), "长时间Sleep");
    }

    @Test(dependsOnMethods = {"testLoad"})
    public void testDump() throws IOException, SAXException {
        String xml = xstream.toXML(test);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Test.xml");
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

    public TestTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(org.jspringbot.report.model.Test.class);
    }

    private org.jspringbot.report.model.Test test;
    private final XStream xstream;
}
