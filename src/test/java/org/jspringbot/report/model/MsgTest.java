package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
public class MsgTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Msg msg = new Msg();
        msg.setTimestamp(new Date(0L));
        msg.setLevel(l);
        msg.setContent(c);
        String xml = xstream.toXML(msg);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Msg.xml");
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
        Msg msg = null;
        try {
            in = getClass().getResourceAsStream("/Msg.xml");
            msg = (Msg) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(msg);
        Assert.assertEquals(msg.getLevel(), l);
        Assert.assertEquals(msg.getContent(), c);
        Assert.assertEquals(msg.getTimestamp().getTime(), 0L);
    }

    public MsgTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd hh:mm:ss.SSS"));
        xstream.processAnnotations(Msg.class);
    }

    private final String l = "INFO";
    private final String c = "Stackoverflow: haha!!${}<>&'\"@";
    private final XStream xstream;
}
