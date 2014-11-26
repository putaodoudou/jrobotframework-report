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
public class StatusTest {

    @Test
    public void testLoad() throws IOException {
        InputStream in = null;
        Status status = null;
        try {
            in = getClass().getResourceAsStream("/Status.xml");
            status = (Status) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(status);
        Assert.assertEquals(status.getStarttime(), starttime);
        Assert.assertEquals(status.getEndtime(), endtime);
        Assert.assertEquals(status.getStatus(), state);
        Assert.assertNull(status.getCritical());
    }

    @Test
    public void testDump() throws IOException, SAXException {
        Status status = new Status();
        status.setStarttime(starttime);
        status.setEndtime(endtime);
        status.setStatus(state);
        String xml = xstream.toXML(status);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Status.xml");
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

    public StatusTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd hh:mm:ss.SSS"));
        xstream.processAnnotations(Status.class);
    }

    private final Date endtime = new Date(1416358713028L + 101L);
    private final Date starttime = new Date(1416358713028L + 31L);
    private final String state = "PASS";
    private final XStream xstream;
}
