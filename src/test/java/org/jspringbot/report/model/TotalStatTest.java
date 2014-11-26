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
public class TotalStatTest {

    @Test
    public void testDump() throws IOException, SAXException {
        TotalStat total = new TotalStat();
        total.setStats(stats);
        String xml = xstream.toXML(total);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/TotalStat.xml");
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
        TotalStat total = null;
        try {
            in = getClass().getResourceAsStream("/TotalStat.xml");
            total = (TotalStat) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(total);
        List<Stat> actualStats = total.getStats();
        Assert.assertEquals(actualStats, stats);
    }

    public TotalStatTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd hh:mm:ss.SSS"));
        xstream.processAnnotations(TotalStat.class);
        stats = new ArrayList<Stat>();
        Stat stat = new Stat();
        stat.setPass(16);
        stat.setFail(1);
        stat.setContent("Critical Tests");
        stats.add(stat);
        stat = new Stat();
        stat.setPass(19);
        stat.setFail(2);
        stat.setContent("All Tests");
        stats.add(stat);
    }

    private final List<Stat> stats;
    private final XStream xstream;
}
