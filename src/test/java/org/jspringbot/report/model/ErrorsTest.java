package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
public class ErrorsTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Errors errors = new Errors();
        errors.setMsgs(msgs);
        String xml = xstream.toXML(errors);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Errors.xml");
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
        Errors errors = null;
        try {
            in = getClass().getResourceAsStream("/Errors.xml");
            errors = (Errors) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(errors);
        List<Msg> actualMsgs = errors.getMsgs();
        Assert.assertEquals(actualMsgs, msgs);
    }

    public ErrorsTest() {
        msgs = new ArrayList<Msg>();
        Msg msg1 = new Msg();
        msg1.setTimestamp(new Date(1416358713028L));
        msg1.setLevel("INFO");
        msg1.setContent("abc");
        msgs.add(msg1);
        Msg msg2 = new Msg();
        msg2.setTimestamp(new Date(1416358714028L));
        msg2.setLevel("WARN");
        msg2.setContent("edf");
        msgs.add(msg2);
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Errors.class);
    }

    private final List<Msg> msgs;
    private final XStream xstream;
}
