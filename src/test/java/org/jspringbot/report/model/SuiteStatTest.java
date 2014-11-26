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
public class SuiteStatTest {

    @Test
    public void testDump() throws IOException, SAXException {
        SuiteStat suiteStat = new SuiteStat();
        suiteStat.setStats(stats);
        String xml = xstream.toXML(suiteStat);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/SuiteStat.xml");
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
        SuiteStat suiteStat = null;
        try {
            in = getClass().getResourceAsStream("/SuiteStat.xml");
            suiteStat = (SuiteStat) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(suiteStat);
        List<Stat> actualSuites = suiteStat.getStats();
        Assert.assertEquals(actualSuites, stats);
    }

    public SuiteStatTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(SuiteStat.class);
        stats = new ArrayList<Stat>();
        Stat stat = new Stat();
        stat.setPass(2);
        stat.setFail(0);
        stat.setName("变量替换传递");
        stat.setId("s1-s1-s2");
        stat.setContent("jspringbot-basic-samples.Builtin.变量替换传递");
        stats.add(stat);
        stat = new Stat();
        stat.setPass(4);
        stat.setFail(0);
        stat.setName("标准条件");
        stat.setId("s1-s3-s1");
        stat.setContent("jspringbot-basic-samples.Condition.标准条件");
        stats.add(stat);
    }

    private final List<Stat> stats;
    private final XStream xstream;
}
