package org.jspringbot.report.model;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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
public class KeywordTest {

    @Test
    public void testDump() throws IOException, SAXException {
        Keyword kw = new Keyword();
        kw.setArguments(arguments);
        kw.setDoc(doc);
        kw.setMsgs(msgs);
        kw.setName(name);
        kw.setStatus(status);
        kw.setType(type);
        String xml = xstream.toXML(kw);
        InputStream in = null;
        String content = null;
        try {
            in = getClass().getResourceAsStream("/Keyword.xml");
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
        Keyword kw = null;
        try {
            in = getClass().getResourceAsStream("/Keyword.xml");
            kw = (Keyword) xstream.fromXML(in);
        } finally {
            if (null != in) {
                in.close();
            }
        }
        Assert.assertNotNull(kw);
        Assert.assertEquals(kw.getArguments(), arguments);
        Assert.assertEquals(kw.getDoc(), doc);
        Assert.assertEquals(kw.getMsgs().get(0), msgs.get(0));
        Assert.assertEquals(kw.getName(), name);
        Assert.assertEquals(kw.getStatus(), status);
        Assert.assertEquals(kw.getType(), type);
    }

    public KeywordTest() {
        xstream = new XStream();
        xstream.registerConverter(new DateConverter("yyyyMMdd HH:mm:ss.SSS"));
        xstream.processAnnotations(Keyword.class);
        arguments = new Arguments();
        arguments.setArgs(Arrays.asList(new String[] {"2 s"}));
        msgs = new ArrayList<Msg>();
        Msg msg = new Msg();
        msgs.add(msg);
        msg.setContent("Slept 2 seconds");
        msg.setLevel("INFO");
        msg.setTimestamp(new Date(1416358713028L));
        status = new Status();
        status.setStatus("PASS");
        status.setStarttime(new Date(1416358713028L + 31L));
        status.setEndtime(new Date(1416358713028L + 101L));
    }

    private final String type = "kw";
    private final String name = "BuiltIn.Sleep";
    private final String doc = "Pauses the test executed for the given time.";
    private final Arguments arguments;
    private final List<Msg> msgs;
    private final Status status;
    private final XStream xstream;
}
