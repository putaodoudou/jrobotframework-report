package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
public class ArgumentsTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Arguments arguments = new Arguments();
        arguments.setArgs(args);
        String xml = xstream.toXML(arguments);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Arguments.xml");
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

    @Test
    public void testLoad() throws IOException {
        InputStream in = null;
        Arguments arguments = null;
        try {
            in = getClass().getResourceAsStream("/Arguments.xml");
            arguments = (Arguments) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(arguments);
        List<String> actualArgs = arguments.getArgs();
        Assert.assertEquals(actualArgs, args);
    }

    public ArgumentsTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Arguments.class);
    }

    private final List<String> args = Arrays.asList(new String[] {"${ORDER_ID}", "6056"});
    private final XStream xstream;
}
