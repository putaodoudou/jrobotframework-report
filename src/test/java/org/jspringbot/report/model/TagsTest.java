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
public class TagsTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Tags tags = new Tags();
        tags.setTags(l);
        String xml = xstream.toXML(tags);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Tags.xml");
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
        Tags tags = null;
        try {
            in = getClass().getResourceAsStream("/Tags.xml");
            tags = (Tags) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(tags);
        Assert.assertEquals(tags.getTags(), l);
    }

    public TagsTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Tags.class);
    }

    private final List<String> l = Arrays.asList(new String[] {"builtin", "P0"});
    private final XStream xstream;
}
