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
public class ItemTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Item item = new Item();
        item.setName(name);
        item.setContent(text);
        String xml = xstream.toXML(item);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Item.xml");
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
        Item item = null;
        try {
            in = getClass().getResourceAsStream("/Item.xml");
            item = (Item) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(item);
        Assert.assertEquals(item.getContent(), text);
        Assert.assertEquals(item.getName(), name);
    }

    public ItemTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Item.class);
    }

    private final String name = "h1";
    private final String text = "h2";
    private final XStream xstream;
}
