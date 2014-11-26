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
public class TagStatTest {

    @Test
    public void testDump() throws IOException, SAXException {
        TagStat tagStat = new TagStat();
        tagStat.setStats(Arrays.asList(new Stat[] {stat}));
        String xml = xstream.toXML(tagStat);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/TagStat.xml");
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
        TagStat tagStat = null;
        try {
            in = getClass().getResourceAsStream("/TagStat.xml");
            tagStat = (TagStat) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(tagStat);
        Assert.assertEquals(tagStat.getStats().size(), 1);
        Assert.assertEquals(tagStat.getStats().get(0), stat);
    }

    public TagStatTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(TagStat.class);
        stat = new Stat();
        stat.setPass(0);
        stat.setFail(1);
        stat.setDoc("");
        stat.setLinks("");
        stat.setCombined("");
        stat.setInfo("");
        stat.setContent("builtin");
    }

    private final Stat stat;
    private final XStream xstream;
}
