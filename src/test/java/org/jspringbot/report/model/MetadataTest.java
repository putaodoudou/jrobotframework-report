package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
public class MetadataTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Metadata metadata = new Metadata();
        metadata.setItems(items);
        String xml = xstream.toXML(metadata);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Metadata.xml");
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
        Metadata metadata = null;
        try {
            in = getClass().getResourceAsStream("/Metadata.xml");
            metadata = (Metadata) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(metadata);
        List<Item> actualItems = metadata.getItems();
        Assert.assertEquals(actualItems, items);
    }

    public MetadataTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd hh:mm:ss.SSS"));
        xstream.processAnnotations(Metadata.class);
        items = new ArrayList<Item>();
        Item item = new Item();
        item.setContent("h2");
        item.setName("h1");
        items.add(item);
    }

    private final List<Item> items;
    private final XStream xstream;
}
