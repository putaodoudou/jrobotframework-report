package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author yanshuai
 */
public class StatTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Stat stat = new Stat();
        stat.setPass(pass);
        stat.setFail(fail);
        stat.setName(name);
        stat.setId(id);
        stat.setContent(content);
        String xml = xstream.toXML(stat);
        InputStream in = null;
        String str = null;
        try {
            in = getClass().getResourceAsStream("/Stat.xml");
            str = IOUtils.toString(in);
        } finally {
            if (null != in) {
                IOUtils.closeQuietly(in);
            }
        }
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(str, xml));
        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals(allDifferences.size(), 0);
    }

    @Test
    public void testLoad() throws IOException {
        InputStream in = null;
        Stat stat = null;
        try {
            in = getClass().getResourceAsStream("/Stat.xml");
            stat = (Stat) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(stat);
        Assert.assertEquals(stat.getContent(), content);
        Assert.assertEquals(stat.getFail(), fail);
        Assert.assertEquals(stat.getPass(), pass);
        Assert.assertEquals(stat.getName(), name);
        Assert.assertEquals(stat.getId(), id);
        Assert.assertNull(stat.getCombined());
        Assert.assertNull(stat.getDoc());
        Assert.assertNull(stat.getInfo());
        Assert.assertNull(stat.getLinks());
    }

    public StatTest() {
        xstream = new XStream();
        xstream.processAnnotations(Stat.class);
    }

    private final int pass = 1024;
    private final int fail = 356;
    private final String name = "hello, world";
    private final String id = "s0-s1";
    private final String content = "haha, this is my content";
    private final XStream xstream;
}
